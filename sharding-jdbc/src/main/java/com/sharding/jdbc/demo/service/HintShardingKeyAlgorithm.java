package com.sharding.jdbc.demo.service;

import com.alibaba.druid.util.StringUtils;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Hint强制路由HintShardingStrategy 这种情况Sharding-jdbc可以使用Hint分片策略来实现各种Sharding-jdbc不支持语法的限制,比如 insert into table1 select * from等SQL,需要结果集归并。
 * 在读写分离数据库中，Hint 可以通过HintManager.setMasterRouteOnly()方法，强制读主库（主从复制存在一定延时，但在某些特定的业务场景中，可能更需要保证数据的实时性）
 */
public class HintShardingKeyAlgorithm implements HintShardingAlgorithm {

    /**
     * 自定义Hint 实现算法
     * 能够保证绕过Sharding-JDBC SQL解析过程
     * @param availableTargetNames
     * @param shardingValue 不再从SQL 解析中获取值，而是直接通过hintManager.addTableShardingValue("t_order", 1)参数指定
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ShardingValue shardingValue) {
        System.out.println("shardingValue=" + shardingValue);
        System.out.println("availableTargetNames=" + availableTargetNames);
        List<String> shardingResult = new ArrayList<>();
        for (String targetName : availableTargetNames) {
            String suffix = targetName.substring(targetName.length() - 1);
            if (StringUtils.isNumber(suffix)) {
                // hint分片算法的ShardingValue有两种具体类型:
                // ListShardingValue和RangeShardingValue
                // 使用哪种取决于HintManager.addDatabaseShardingValue(String, String, ShardingOperator,...),ShardingOperator的类型
                ListShardingValue<Integer> tmpSharding = (ListShardingValue<Integer>) shardingValue;
                for (Integer value : tmpSharding.getValues()) {
                    if (value % 2 == Integer.parseInt(suffix)) {
                        shardingResult.add(targetName);
                    }
                }
            }
        }
        return shardingResult;
    }
}