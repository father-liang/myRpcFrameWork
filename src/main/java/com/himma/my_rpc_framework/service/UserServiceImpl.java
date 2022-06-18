package com.himma.my_rpc_framework.service;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Integer id) {
        System.out.println("接收到客户端发来的id: " + id);
        return User.builder().name("梁子祥").age(21).sex("男").build();
    }

    @Override
    public int insertUser(User user) {
        System.out.println("接收到客户端发来的user: " + user.toString());
        return user.getAge();
    }
}
