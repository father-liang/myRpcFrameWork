package com.himma.my_rpc_framework.common;

import java.util.List;

public class RoundLoadBalance implements LoadBalance {

    private volatile int choose = -1;

    @Override
    public String balance(List<String> addressList) {
        choose++;

        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}
