package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.RpcRequest;
import com.himma.my_rpc_framework.common.RpcResponse;
import com.himma.my_rpc_framework.common.ServiceRegister;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;



public class BioRpcClient extends BasicRpcClient implements RpcClient {
    private ServiceRegister serviceRegister;

    public BioRpcClient(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        InetSocketAddress inetSocketAddress = serviceRegister.serviceDiscovery(request.getInterfaceName());
        this.setHost(inetSocketAddress.getHostName());
        this.setPort(inetSocketAddress.getPort());
        try {
            Socket socket = new Socket(getHost(), getPort());

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("已发送请求: " + request.toString());

            return (RpcResponse) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("客户端发送请求出现IO问题");
            return null;
        }
    }
}
