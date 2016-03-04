package client;

import config.IConfig;
import request.IRequest;
import response.IResponse;

public class AbstractClient implements IClient {
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

    public IConfig getDefaultConfig() {
        return null;
    }
}
