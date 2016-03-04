package client;

import request.IRequest;
import response.IResponse;

public interface IClient {
    IResponse get(IRequest request) throws ClientException;
    IResponse put(IRequest request) throws ClientException;
    IResponse delete(IRequest request) throws ClientException;
    IResponse post(IRequest request) throws ClientException;
}
