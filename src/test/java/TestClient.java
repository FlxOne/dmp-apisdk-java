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

/**
 * Created by rv186025 on 04/03/16.
 */
public class TestClient {
    @Test
    public void testClient() throws ClientException {
        // Conf
        IConfig conf = Config.getDefault();
        conf.setCredentials(System.getProperty("DMP_USERNAME"), System.getProperty("DMP_PASSWORD"));
        conf.setEndpoint(System.getProperty("DMP_ENDPOINT", conf.getEndpoint()));

        // Client
        IClient client = new Client(conf);

        // Request
        IRequest req = new Request("user/current");

        // Execute
        IResponse resp = client.get(req);

        // Validate ID
        assertTrue(resp.getAsJsonObject("user").get("id").getAsString().length() > 0);
    }
}
