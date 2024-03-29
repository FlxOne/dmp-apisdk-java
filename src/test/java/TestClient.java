import com.teradata.dmp.apisdk.client.AbstractClient;
import com.teradata.dmp.apisdk.client.Client;
import com.teradata.dmp.apisdk.client.ClientException;
import com.teradata.dmp.apisdk.client.IClient;
import com.teradata.dmp.apisdk.config.Config;
import com.teradata.dmp.apisdk.config.IConfig;
import com.teradata.dmp.apisdk.request.IRequest;
import com.teradata.dmp.apisdk.request.Request;
import com.teradata.dmp.apisdk.response.IResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestClient {
    public void testClient() throws ClientException {
        // Conf
        IConfig conf = Config.getDefault();

        // Credentials are read from system env, put in ~/.bashrc (e.g. export DMP_USERNAME=test )
        // in order to take effect you might need to reload env, restart editor or reboot machine
        conf.setCredentials(System.getenv("DMP_USERNAME"), System.getenv("DMP_PASSWORD"));
        String endpoint = System.getenv("DMP_ENDPOINT");
        if (endpoint != null) {
            conf.setEndpoint(endpoint);
        }

        // Client
        IClient client = new Client(conf);

        // Request
        IRequest req = new Request("user/current");

        // Execute
        IResponse resp = client.get(req);

        // Validate ID
        assertTrue(resp.getAsJsonObject("user").getAsJsonPrimitive("id").getAsString().length() > 0);

        // Access underlying methods
        AbstractClient aClient = (AbstractClient)client;

        // Expire session
        aClient.resetAuthToken();

        // Execute again
        IRequest req2 = new Request("user/current");
        IResponse resp2 = aClient.get(req2);

        // Validate ID
        assertTrue(resp2.getAsJsonObject("user").getAsJsonPrimitive("id").getAsString().length() > 0);

        // Test invalid session ID
        aClient.setAuthToken("asdf");

        // Execute again
        IRequest req3 = new Request("user/current");
        IResponse resp3 = aClient.get(req3);

        // Validate ID
        assertTrue(resp3.getAsJsonObject("user").getAsJsonPrimitive("id").getAsString().length() > 0);
    }

    public void testClientHMACOption() throws ClientException {
        // Conf
        IConfig conf = Config.getDefault();
        conf.setHMACEnabled(true);

        // Credentials are read from system env, put in ~/.bashrc (e.g. export DMP_USERNAME=test )
        // in order to take effect you might need to reload env, restart editor or reboot machine
        conf.setCredentials(System.getenv("DMP_USERNAME"), System.getenv("DMP_PASSWORD"));
        String endpoint = System.getenv("DMP_ENDPOINT");

        conf.setHMACSecret(System.getenv("DMP_HMAC_SECRET"));

        if (endpoint != null) {
            conf.setEndpoint(endpoint);
        }

        // Client
        IClient client = new Client(conf);

        // Request
        IRequest req = new Request("user/current");

        // Execute
        IResponse resp = client.get(req);

        // Validate ID
        assertTrue(resp.getAsJsonObject("user").getAsJsonPrimitive("id").getAsString().length() > 0);
    }

}
