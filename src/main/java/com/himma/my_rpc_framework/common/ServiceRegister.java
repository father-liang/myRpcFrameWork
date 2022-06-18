package com.himma.my_rpc_framework.common;

import java.net.InetSocketAddress;

public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress serverAddress);

    InetSocketAddress serviceDiscovery(String serviceName);
}
