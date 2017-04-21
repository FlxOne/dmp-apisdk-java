package com.teradata.dmp.apisdk.client;

import com.teradata.dmp.apisdk.config.IConfig;
import com.teradata.dmp.apisdk.request.IRequest;
import com.teradata.dmp.apisdk.request.Request;
import com.teradata.dmp.apisdk.response.IResponse;
import com.teradata.dmp.apisdk.response.Response;
import com.teradata.dmp.apisdk.response.ResponseStatus;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


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
        // Need to login?
        if (!isAuthRequest(req)) {
            if (authToken == null || csrfToken == null || authToken.isEmpty() || csrfToken.isEmpty()) {
                if (!authenticate()) {
                    throw new ClientException(new Exception("Failed to authenticate"));
                }
            }
        }

        // Execute
        CloseableHttpResponse cHttpResp = null;
        Response resp = null;
        String responseStr = "";
        for (int i = 0; i < config.getMaxRetries(); i++) {
            logger.debug("Starting attempt " + i);
            try {
                // Set headers (except when doing the auth call)
                if (!req.getURI().toString().toLowerCase().contains("/auth")) {
                    logger.debug("Adding auth headers");
                    req.setHeader("X-Auth", authToken);
                    req.setHeader("X-CSRF", csrfToken);
                }

                // Execute request
//                System.out.println(new Gson().toJson(req.getAllHeaders()));
                cHttpResp = this.client.execute(req);

                // Status
                int status = cHttpResp.getStatusLine().getStatusCode();
                if (status == 401) {
//                    System.out.println(req);
                    logger.warn("Received 401, doing auth");

                    // Consume so it doesn't hang
                    EntityUtils.consumeQuietly(cHttpResp.getEntity());

                    // Re-auth
                    authenticate();

                    // Try again
                    continue;
                }

                // Parse response
                responseStr = closeableHttpResponseToString(cHttpResp);
                resp = new Response(responseStr);

                // Valid?
                if (resp.getStatus().equals(ResponseStatus.OK)) {
                    // OK, stop retrying
                    break;
                }
            } catch (HttpResponseException httpRespX) {
                // Consume so it doesn't hang
                if (cHttpResp != null) {
                    EntityUtils.consumeQuietly(cHttpResp.getEntity());
                }

                // Log error
                logger.error("Failed request response type", httpRespX);

                // Session expired?
                if (httpRespX.getStatusCode() == 401) {
                    // Re-auth
                    authenticate();
                }

                // Next iteration of for loop will try again
            } catch (Exception x) {
                // Consume so it doesn't hang
                if (cHttpResp != null) {
                    EntityUtils.consumeQuietly(cHttpResp.getEntity());
                }

                // Handle + exponential backoff
                logger.error("Failed request unknown", x);

                // Exponential backoff with jitter
                try {
                    Thread.sleep((1000 * i * i) + random.nextInt(100));
                } catch (InterruptedException ix) {
                    // Ignore
                }
            }
        }

        // got the response, do we need to check for HMAC? only if not auth, and configured in config.
        if (resp != null && cHttpResp != null && !isAuthRequest(req)) {
            if(config.isHMACEnabled()) {
                validateHMAC(cHttpResp, responseStr);
            }
        }

        return resp;
    }

    private boolean isAuthRequest(HttpUriRequest req) {
        return req.getURI().toString().toLowerCase().contains("/auth");
    }

    private void validateHMAC(CloseableHttpResponse cHttpResp, String responseStr)
        throws ClientException {
        Header xHMAC = cHttpResp.getFirstHeader("X-HMAC");
        if (xHMAC == null){
            logger.error("HMAC validation not passed, no X-HMAC header present");
            throw new ClientException(new HttpResponseException(401, "HMAC header not"
                + " present but required to be checked"));
        }
        String receivedHMAC = xHMAC.getValue();
        String responseHMAC = "";
        try {
            responseHMAC = encodeResponseHmac(responseStr, config.getHMACSecret());
        } catch (Exception e) {
            logger.error("Error encoding HMAC from responseSTR", e);
            throw new ClientException(e);
        }

        if(!responseHMAC.equals(receivedHMAC)) {
            throw new ClientException(new HttpResponseException(401, "HMAC header received "
                + "is not equal the encoded response"));
        }
    }

    private String encodeResponseHmac(String data, String HMACSecret) throws NoSuchAlgorithmException,
        InvalidKeyException, UnsupportedEncodingException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(HMACSecret.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
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

    public void resetAuthToken() {
        authToken = "";
    }

    public void setAuthToken(String v) {
        authToken = v;
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
