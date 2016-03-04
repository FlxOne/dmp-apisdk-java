package client;

import config.Config;
import config.IConfig;
import request.IRequest;
import response.IResponse;

public abstract class AbstractClient implements IClient {

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
