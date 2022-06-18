package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.RpcRequest;
import com.himma.my_rpc_framework.common.RpcResponse;

public interface RpcClient {
    RpcResponse sendRequest(RpcRequest request);
}
