package client;

import config.IConfig;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import request.IRequest;
import response.IResponse;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;


public abstract class AbstractClient implements IClient {
    protected CloseableHttpClient client;
    protected IConfig config;

    public AbstractClient(IConfig config) {
        this.config = config;
        this.client = HttpClients.createDefault();
    }

    public IResponse get(IRequest request) throws ClientException {
        try {
            HttpGet getRequest = new HttpGet(this.getURIBuilderForRequest(request).build());
            return execute(getRequest);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    protected IResponse execute(HttpUriRequest req) {
        CloseableHttpResponse cHttpResp;
        Response resp = null;
        for (int i = 0; i < config.getMaxRetries(); i++) {
            try {
                // Execute request
                cHttpResp = this.client.execute(req);

                // Parse response
                resp = new Response(closeableHttpResponseToString(cHttpResp));

                // Valid?
                if (resp.getStatus().equals(ResponseStatus.OK)) {
                    // OK, stop retrying
                    break;
                }
            } catch (Exception x) {
                // Handle + exponential backoff
                // @todo check if session dead
            }
        }
        return resp;
    }

    public IResponse put(IRequest request) {
        return null;
    }

    public IResponse delete(IRequest request) {
        return null;
    }

    public IResponse post(IRequest request) {
        return null;
    }

    protected URIBuilder getURIBuilderForRequest(IRequest request) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(this.config.getEndpoint() + "/" + request.getService());
        for(Map.Entry<String, String> entry : request.getParameters().entrySet()) {
            uriBuilder.setParameter(entry.getKey(), entry.getValue());
        }
        return uriBuilder;
    }

    protected String closeableHttpResponseToString(CloseableHttpResponse cHttpResp) throws
        IOException, ParseException {
        return EntityUtils.toString(cHttpResp.getEntity());
    }
}
