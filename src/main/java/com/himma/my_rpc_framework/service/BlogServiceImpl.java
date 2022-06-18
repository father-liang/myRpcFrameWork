package com.himma.my_rpc_framework.service;

public class BlogServiceImpl implements BlogService {

    @Override
    public Blog getBlogById(Integer id) {
        return Blog.builder().id(id).userId(id).title("查询到的Blog").build();
    }
}
