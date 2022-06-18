package com.himma.my_rpc_framework.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse implements Serializable {

    private String code;

    private String message;
    private Class<?> dataType;

    private Object data;

    public static RpcResponse success(Object result) {
        return new RpcResponse("200", "成功", result.getClass(), result);
    }

    public static RpcResponse error() {
        return RpcResponse.builder().code("500").message("服务器出现异常").build();
    }
}
