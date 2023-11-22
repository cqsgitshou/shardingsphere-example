package com.sharding.jdbc.demo.service;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 复合分片策略
 * @author 1
 * @Date : 2023/11/21
 */
public class ComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    /**
     *
     * @param collection 在加载配置文件时，会解析表分片规则。将结果存储到 collection中，doSharding（）参数使用
     * @param shardingValues SQL中对应的
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> shardingValues) {
        System.out.println("collection:" + collection + ",shardingValues:" + shardingValues);

        Collection<Integer> orderIdValues = getShardingValue(shardingValues, "order_id");
        Collection<Integer> userIdValues = getShardingValue(shardingValues, "user_id");

        List<String> shardingSuffix = new ArrayList<>();

        // user_id，order_id分片键进行分表
        for (Integer userId : userIdValues) {
            for (Integer orderId : orderIdValues) {
                String suffix = userId % 2 + "_" + orderId % 2;
                for (String s : collection) {
                    if (s.endsWith(suffix)) {
                        shardingSuffix.add(s);
                    }
                }
            }
        }

        return shardingSuffix;
    }

    /**
     * 例如: SELECT * FROM T_ORDER user_id = 100000 AND order_id = 1000009
     * 循环 获取SQL 中 分片键列对应的value值
     * @param shardingValues sql 中分片键的value值   -> 1000009
     * @param key 分片键列名                        -> user_id
     * @return shardingValues 集合                 -> [1000009]
     */
    private Collection<Integer> getShardingValue(Collection<ShardingValue> shardingValues, final String key) {
        Collection<Integer> valueSet = new ArrayList<>();
        Iterator<ShardingValue> iterator = shardingValues.iterator();
        while (iterator.hasNext()) {
            ShardingValue next = iterator.next();
            if (next instanceof ListShardingValue) {
                ListShardingValue value = (ListShardingValue) next;
                // user_id，order_id 分片键进行分表
                if (value.getColumnName().equals(key)) {
                    return value.getValues();
                }
            }
        }
        return valueSet;
    }
}