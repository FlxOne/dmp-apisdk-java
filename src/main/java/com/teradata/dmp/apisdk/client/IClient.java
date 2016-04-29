package com.teradata.dmp.apisdk.client;

import com.teradata.dmp.apisdk.request.IRequest;
import com.teradata.dmp.apisdk.response.IResponse;

public interface IClient {
    IResponse get(IRequest request) throws ClientException;
    IResponse put(IRequest request) throws ClientException;
    IResponse delete(IRequest request) throws ClientException;
    IResponse post(IRequest request) throws ClientException;
}
