package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.service.*;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8081);
        serviceProvider.provideInterface(userService);
        serviceProvider.provideInterface(blogService);

        RPCServer server = new NettyRpcServer(serviceProvider);
        server.start(8081);
    }
}
