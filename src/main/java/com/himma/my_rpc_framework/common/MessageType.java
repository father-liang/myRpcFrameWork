package com.himma.my_rpc_framework.common;

public enum MessageType {
    REQUEST("request", 0),
    RESPONSE("response", 1);


    private String name;
    private Integer code;

    MessageType(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
