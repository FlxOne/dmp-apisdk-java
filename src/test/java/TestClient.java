import client.Client;
import client.ClientException;
import client.IClient;
import config.Config;
import config.IConfig;
import org.junit.Test;
import request.IRequest;
import request.Request;
import response.IResponse;

import static junit.framework.TestCase.assertTrue;

public class TestClient {
    @Test
    public void testClient() throws ClientException {
        // Conf
        IConfig conf = Config.getDefault();
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
