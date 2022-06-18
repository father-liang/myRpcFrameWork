package com.himma.my_rpc_framework.common;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}
