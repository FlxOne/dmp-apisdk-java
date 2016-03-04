package client;

import config.IConfig;

public class Client extends AbstractClient implements IClient {
    protected IConfig config;

    public Client(IConfig config) {
        this.config = config;
    }
}
