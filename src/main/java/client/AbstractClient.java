package client;

import config.Config;
import config.IConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import request.IRequest;
import response.IResponse;

public abstract class AbstractClient implements IClient {
    protected CloseableHttpClient client;
    protected IConfig config;

    public AbstractClient(IConfig config) {
        this.config = config;
        this.client = HttpClients.createDefault();
    }

    public IResponse get(IRequest request) {
        return null;
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
}
