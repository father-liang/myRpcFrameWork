package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.common.RpcRequest;
import com.himma.my_rpc_framework.common.RpcResponse;
import com.himma.my_rpc_framework.service.ServiceProvider;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class BioWorkThread extends WorkThread implements Runnable {

    private final Socket socket;

    public BioWorkThread(ServiceProvider serviceProvider, Socket socket) {
        super(serviceProvider);
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(getResponse((RpcRequest)ois.readObject()));
            oos.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private RpcResponse getResponse(RpcRequest rpcRequest) {

        Object service = getServiceProvider().getService(rpcRequest.getInterfaceName());

        Object invoke = null;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsTypes());

            invoke = method.invoke(service, rpcRequest.getParams());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return RpcResponse.error();
        }

        return RpcResponse.success(invoke);

    }
}
