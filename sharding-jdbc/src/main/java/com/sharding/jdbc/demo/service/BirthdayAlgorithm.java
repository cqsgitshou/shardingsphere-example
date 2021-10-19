package com.sharding.jdbc.demo.service;
import cn.hutool.core.date.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.*;
/**
 * @author: 学相伴-飞哥
 * @description: BirthdayAlgorithm
 * @Date : 2021/3/11
 */
public class BirthdayAlgorithm implements PreciseShardingAlgorithm<String> {
    List<Date> dateList = new ArrayList<>();
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2020, 1, 1, 0, 0, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2021, 1, 1, 0, 0, 0);

        dateList.add(calendar1.getTime());
        dateList.add(calendar2.getTime());

    }
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        // 获取属性数据库的值
        String date = preciseShardingValue.getValue();
        Date d = DateUtil.parse(date,"yyyy-MM-dd");
        // 获取数据源的名称信息列表
        Iterator<String> iterator = collection.iterator();
        String target = null;
        for (Date s : dateList) {
            target = iterator.next();
            // 如果数据晚于指定的日期直接返回
            if (d.before(s)) {
                break;
            }
        }
        return target;
    }
}