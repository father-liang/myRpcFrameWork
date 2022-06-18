package com.himma.my_rpc_framework.client;

import com.himma.my_rpc_framework.common.RoundLoadBalance;
import com.himma.my_rpc_framework.service.Blog;
import com.himma.my_rpc_framework.service.BlogService;
import com.himma.my_rpc_framework.service.User;
import com.himma.my_rpc_framework.service.UserService;

public class TestClient {

    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy(new NettyRpcClient(new RoundLoadBalance()));

        UserService userService = clientProxy.getProxy(UserService.class);

        User user = userService.getUserById(1);

        System.out.println("从服务器获取到user： " + user.toString());

        BlogService blogService = clientProxy.getProxy(BlogService.class);

        Blog blog = blogService.getBlogById(1);

        System.out.println("从服务器获取到blog:  " + blog.toString());
    }
}
