package com.himma.my_rpc_framework.common;

import java.net.InetSocketAddress;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

@Slf4j
public class ZKServiceRegister implements ServiceRegister {

    private LoadBalance loadBalance;
    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";
    private static final String HOST = "192.168.0.142:2181";
    private static final Integer SESSION_TIMEOUT = 40000;

    public ZKServiceRegister(LoadBalance loadBalance) {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(HOST)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(ROOT_PATH)
                .build();
        this.client.start();
        this.loadBalance = loadBalance;
        log.info("zookeeper 连接成功");
    }

    public ZKServiceRegister() {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(HOST)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(ROOT_PATH)
                .build();
        this.client.start();
        this.loadBalance = new RoundLoadBalance();
        log.info("zookeeper 连接成功");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            if (this.client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }

            String serviceAddress = getServiceAddress(serviceName, serverAddress);
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(serviceAddress);
            log.info("服务： " + serviceName + " 已经注册到zookeeper，注册地址为 " + serviceAddress);
        } catch (Exception e) {
            log.info(serviceName + " 服务已经存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> serviceAddressList = this.client.getChildren().forPath("/" + serviceName);

            String serviceAddress = loadBalance.balance(serviceAddressList);

            return parseAddress(serviceAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private InetSocketAddress parseAddress(String serviceAddress) {
        String[] split = serviceAddress.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

    private String getServiceAddress(String serviceName, InetSocketAddress serverAddress) {
        return new StringBuilder().append("/")
                .append(serviceName)
                .append("/")
                .append(serverAddress.getHostName())
                .append(":")
                .append(serverAddress.getPort())
                .toString();
    }
}
