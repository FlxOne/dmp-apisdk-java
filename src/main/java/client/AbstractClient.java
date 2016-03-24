package client;

import config.IConfig;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.IRequest;
import request.Request;
import response.IResponse;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;


public abstract class AbstractClient implements IClient {
    protected CloseableHttpClient client;
    protected IConfig config;
    protected String authToken = null;
    protected String csrfToken = null;
    protected Random random;
    private static final Logger logger = LogManager.getLogger(AbstractClient.class);

    public AbstractClient(IConfig config) {
        this.config = config;
        this.client = HttpClients.createDefault();
        this.random = new Random();
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
        logger.info("Executing " + req.getMethod() + " request to " + req.getURI());

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

                // Exponential backoff with jitter
                try {
                    Thread.sleep((1000 * i * i) + random.nextInt(100));
                } catch (InterruptedException ix) {
                    // Ignore
                }
            }
        }
        return resp;
    }

    public IResponse get(IRequest request) throws ClientException {
        try {
            HttpGet r = new HttpGet(this.getURIBuilderForRequest(request).build());
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public IResponse put(IRequest request) throws ClientException {
        try {
            HttpPut r = new HttpPut(this.config.getEndpoint() + "/" + request.getService());
            r.setEntity(createPostBody(request));
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public IResponse delete(IRequest request) throws ClientException {
        try {
            HttpDelete r = new HttpDelete(this.getURIBuilderForRequest(request).build());
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public IResponse post(IRequest request) throws ClientException {
        try {
            HttpPost r = new HttpPost(this.config.getEndpoint() + "/" + request.getService());
            r.setEntity(createPostBody(request));
            return execute(r);
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    protected URIBuilder getURIBuilderForRequest(IRequest request) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(this.config.getEndpoint() + "/" + request.getService());
        for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
            uriBuilder.setParameter(entry.getKey(), entry.getValue());
        }
        return uriBuilder;
    }

    protected HttpEntity createPostBody(IRequest request) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
            builder.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        String params = builder.toString();
        HttpEntity entity = new ByteArrayEntity(params.getBytes("UTF-8"));
        return entity;
    }

    protected String closeableHttpResponseToString(CloseableHttpResponse cHttpResp) throws
            IOException, ParseException {
        return EntityUtils.toString(cHttpResp.getEntity());
    }
}
