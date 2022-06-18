package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.service.ServiceProvider;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BioRpcServer extends BasicPpcServer implements RPCServer {

    BioRpcServer(ServiceProvider serviceProvider) {
        super(serviceProvider);
    }

    BioRpcServer(ServiceProvider serviceProvider,
            int corePoolSize,
            int maxPoolSize,
            int keepAliveTime,
            TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(serviceProvider, corePoolSize, maxPoolSize, keepAliveTime, timeUnit, blockingQueue);
    }

    @Override
    public void start(int port) {
        System.out.println("服务端启动了");

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();

                this.getThreadPool().execute(new BioWorkThread(this.getServiceProvider(), socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}

