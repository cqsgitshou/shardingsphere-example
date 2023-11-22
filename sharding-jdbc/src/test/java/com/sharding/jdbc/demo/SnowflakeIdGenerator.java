package com.sharding.jdbc.demo;

public class SnowflakeIdGenerator {
    // 起始的时间戳，可以设置为你系统的合适时间点
    private final long START_TIMESTAMP = 1609459200000L; // 2021-01-01 00:00:00

    // 每部分占用的位数
    private final long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final long DATA_CENTER_BIT = 5; // 数据中心占用的位数

    // 每部分的最大值
    private final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);

    // 每部分向左的位移
    private final long MACHINE_LEFT = SEQUENCE_BIT;
    private final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId;  // 数据中心
    private long machineId;     // 机器标识
    private long sequence = 0L;  // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳

    public SnowflakeIdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("Data Center ID can't be greater than " + MAX_DATA_CENTER_NUM + " or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public synchronized long generateId() {
        long currentTimestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，应等待时钟追赶
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - currentTimestamp) + " milliseconds");
        }

        // 如果是同一时间生成的，则进行序列号累加
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 溢出处理
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence = 0L;
        }

        // 更新上一次生成ID的时间戳
        lastTimestamp = currentTimestamp;

        // 进行移位操作生成最终的ID，并返回
        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT) |
               (dataCenterId << DATA_CENTER_LEFT) |
               (machineId << MACHINE_LEFT) |
               sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        // 创建一个SnowflakeIdGenerator，传入数据中心ID和机器ID
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(5, 5);

        // 生成10个ID并打印
        for (int i = 0; i < 10; i++) {
            long id = idGenerator.generateId();
            System.out.println("Generated ID: " + id);
        }
    }
}
