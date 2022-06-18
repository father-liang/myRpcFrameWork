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
public class RpcRequest implements Serializable {

    private String interfaceName;

    private String methodName;

    private Object[] params;

    private Class<?>[] paramsTypes;
}
