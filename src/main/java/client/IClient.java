package client;

import config.IConfig;
import request.IRequest;
import response.IResponse;

public interface IClient {
    IResponse get(IRequest request);
    IResponse put(IRequest request);
    IResponse delete(IRequest request);
    IResponse post(IRequest request);
}
