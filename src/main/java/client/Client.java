package client;

import config.IConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client extends AbstractClient implements IClient {
    protected IConfig config;
    protected CloseableHttpClient client;

    public Client(IConfig config) {
        this.config = config;
        this.client = HttpClients.createDefault();
    }
}
