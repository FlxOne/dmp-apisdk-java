package client;

import request.IRequest;
import response.IResponse;

public interface IClient {
    IResponse get(IRequest request) throws Exception;
    IResponse put(IRequest request) throws Exception;
    IResponse delete(IRequest request) throws Exception;
    IResponse post(IRequest request) throws Exception;
}
