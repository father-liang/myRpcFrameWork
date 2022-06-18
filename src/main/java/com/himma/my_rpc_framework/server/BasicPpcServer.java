package com.himma.my_rpc_framework.server;

import com.himma.my_rpc_framework.service.ServiceProvider;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Data;

@Data
public class BasicPpcServer {

    private ThreadPoolExecutor threadPool;
    private ServiceProvider serviceProvider;

    BasicPpcServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
    }

    BasicPpcServer(ServiceProvider serviceProvider,
            int corePoolSize,
            int maxPoolSize,
            int keepAliveTime,
            TimeUnit timeUnit,
            BlockingQueue<Runnable> blockingQueue) {
        this.serviceProvider = serviceProvider;

        this.threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit, blockingQueue);
    }

    public ServiceProvider getServiceProvider(){
        return this.serviceProvider;
    }
}
