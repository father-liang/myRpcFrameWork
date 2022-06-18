package com.himma.my_rpc_framework.service;

public interface UserService {

    User getUserById(Integer id);

    int insertUser(User user);
}
