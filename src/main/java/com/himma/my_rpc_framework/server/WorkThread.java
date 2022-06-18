package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.service.ServiceProvider;
import java.util.Map;
import lombok.Data;

@Data
public class WorkThread {
    private ServiceProvider serviceProvider;

    public WorkThread(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
