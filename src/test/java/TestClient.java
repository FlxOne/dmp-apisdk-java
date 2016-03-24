import com.teradata.dmp.apisdk.client.Client;
import com.teradata.dmp.apisdk.client.ClientException;
import com.teradata.dmp.apisdk.client.IClient;
import com.teradata.dmp.apisdk.config.Config;
import com.teradata.dmp.apisdk.config.IConfig;
import org.junit.Test;
import com.teradata.dmp.apisdk.request.IRequest;
import com.teradata.dmp.apisdk.request.Request;
import com.teradata.dmp.apisdk.response.IResponse;

import static junit.framework.TestCase.assertTrue;

public class TestClient {
    @Test
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
    }
}
