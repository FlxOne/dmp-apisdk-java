package client;

import config.IConfig;
import org.apache.http.ParseException;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import request.IRequest;
import request.Request;
import response.IResponse;
import response.JsonElement;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;


public abstract class AbstractClient implements IClient {
    protected CloseableHttpClient client;
    protected IConfig config;
    protected String authToken = null;
    protected String csrfToken = null;

    public AbstractClient(IConfig config) {
        this.config = config;
        this.client = HttpClients.createDefault();
    }

    public IResponse get(IRequest request) throws ClientException {
        try {
            HttpGet r = new HttpGet(this.getURIBuilderForRequest(request).build());
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    protected boolean authenticate() throws ClientException {
        IRequest req = new Request("auth");
        req.setParameter("username", config.getUsername());
        req.setParameter("password", config.getPassword());
        IResponse resp = post(req);
        if (resp == null) {
            return false;
        }
        authToken = resp.getAsJsonPrimitive("token").getAsString();
        csrfToken = resp.getCsrfToken();
        return true;
    }

    protected IResponse execute(HttpUriRequest req) throws ClientException {
        // Need to login?
        if (!req.getURI().toString().toLowerCase().contains("auth")) {
            if (authToken == null || csrfToken == null) {
                if (!authenticate()) {
                    throw new ClientException(new Exception("Failed to authenticate"));
                }
            }
        }

        // Headers
        req.addHeader("X-Auth", authToken);
        req.addHeader("X-CSRF", csrfToken);

        // Execute
        CloseableHttpResponse cHttpResp;
        Response resp = null;
        for (int i = 0; i < config.getMaxRetries(); i++) {
            try {
                // Execute request
                cHttpResp = this.client.execute(req);

                // Status
                int status = cHttpResp.getStatusLine().getStatusCode();
                if (status == 401) {
                    authenticate();

                    // Try again
                    continue;
                }

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
                System.err.println(x);
                x.printStackTrace();
            }
        }
        return resp;
    }

    public IResponse put(IRequest request) throws ClientException {
        try {
            HttpPut r = new HttpPut(this.getURIBuilderForRequest(request).build());
            // @todo body, not use uri builder above
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public IResponse delete(IRequest request) throws ClientException {
        try {
            HttpDelete r = new HttpDelete(this.getURIBuilderForRequest(request).build());
            // @todo body, not use uri builder above
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public IResponse post(IRequest request) throws ClientException {
        try {
            HttpPost r = new HttpPost(this.getURIBuilderForRequest(request).build());
            // @todo body, not use uri builder above
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
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
