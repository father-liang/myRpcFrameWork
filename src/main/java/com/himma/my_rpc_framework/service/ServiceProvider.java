package com.himma.my_rpc_framework.service;

import com.himma.my_rpc_framework.common.ServiceRegister;
import com.himma.my_rpc_framework.common.ZKServiceRegister;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private Integer port;

    public ServiceProvider(String host, Integer port) {
        this.interfaceProvider = new HashMap<>();
        this.host = host;
        this.port = port;
        this.serviceRegister = new ZKServiceRegister();
    }

    public void provideInterface(Object service){
        for (Class<?> clazz : service.getClass().getInterfaces()) {
            this.serviceRegister.register(clazz.getName(), new InetSocketAddress(this.host, this.port));
            interfaceProvider.put(clazz.getName(), service);
        }
    }

    public Map<String, Object> getInterfaceProvider() {
        return interfaceProvider;
    }

    public void setInterfaceProvider(Map<String, Object> interfaceProvider) {
        this.interfaceProvider = interfaceProvider;
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
