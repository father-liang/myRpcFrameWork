package com.himma.my_rpc_framework.common;

import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomLoadBalance implements LoadBalance{

    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();

        int nextInt = random.nextInt(addressList.size());

        log.info("负载均衡选择了" + addressList.get(nextInt) + "服务器");
        return addressList.get(nextInt);
    }
}
