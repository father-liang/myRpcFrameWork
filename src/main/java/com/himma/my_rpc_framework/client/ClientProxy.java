package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.RpcRequest;
import com.himma.my_rpc_framework.common.RpcResponse;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientProxy implements InvocationHandler {

    private RpcClient rpcClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramsTypes(method.getParameterTypes())
                .params(args).build();

        RpcResponse response = rpcClient.sendRequest(request);

        return response.getData();
    }

    public <T>T getProxy(Class<T> clazz){
        Object proxyInstance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);

        return ((T) proxyInstance);
    }
}
