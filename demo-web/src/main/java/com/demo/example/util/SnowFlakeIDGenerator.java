package com.demo.example.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import static java.util.regex.Pattern.compile;

/**
 * @author: lijiawei04
 * @date: 2021/3/26 2:25 下午
 */
@Slf4j
public class SnowFlakeIDGenerator {

    private static final String HOST_NAME = "HOSTNAME";
    private static final String DATACENTER_ID_ENV = "DATACENTER";

    /**
     * 起始的时间戳 2018-12-01 17:16:36
     */
    private final static long START_STMP = 1543655796000L;
    /**
     * 序列号占用的位数
     */
    // private final static long SEQUENCE_BIT = 12;
    private final static long SEQUENCE_BIT = 9;
    /**
     * 机器标识占用的位置
     * 将机器位由7改为9，产生的id由16位扩容成17位
     */
    // private final static long MACHINE_BIT = 5;
    private final static long MACHINE_BIT = 8;
    /**
     * 数据中心占用的位置，考虑到我司基本上用不到这个bit，所以全给machine_bit了
     */
    // private final static long DATACENTER_BIT = 5;
    private final static long DATACENTER_BIT = 0;
    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    /**
     * 数据中心标识
     */
    private final long datacenterId;
    /**
     * 机器标识
     */
    private final long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;
    private static final SnowFlakeIDGenerator DEFAULT_GENERATOR = new SnowFlakeIDGenerator(
            Optional.ofNullable(System.getenv(DATACENTER_ID_ENV)).map(Integer::parseInt)
                    .orElse((int) (System.currentTimeMillis() % (1 << DATACENTER_BIT))),
            Optional.ofNullable(getMachineId()).orElse((int) (System.currentTimeMillis() % (1 << MACHINE_BIT))));

    public SnowFlakeIDGenerator(final long datacenterId, final long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public static long nextNumber() {
        return DEFAULT_GENERATOR.nextId();
    }

    public static long nextNumber(byte prefix) {
        return DEFAULT_GENERATOR.nextId(prefix);
    }
    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (currStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = sequence + 1 & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStmp = currStmp;
        // 时间戳部分 + 数据中心部分 + 机器标识部分 + 序列号部分
        return currStmp - START_STMP << TIMESTMP_LEFT | datacenterId << DATACENTER_LEFT | machineId << MACHINE_LEFT
                | sequence;
    }
    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId(byte prefix) {
        assert prefix < 10 && prefix > 0;
        long id = nextId();
        return Long.parseLong(prefix + "" + id);
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private static Integer getMachineId() {
        String hostname = System.getenv(HOST_NAME);
        if (isEmpty(hostname)) {
            hostname = getMachineIdByInetAddress();
            if (isEmpty(hostname)) {
                return null;
            }
        }
        log.info("SnowFlakeIDGenerator get hostName:{}", hostname);
        // 匹配hostname最后的数字作为机器的id
        Pattern compile = compile("\\d+$");
        Matcher matcher = compile.matcher(hostname);
        return matcher.find() ? Integer.parseInt(matcher.group()) : null;
    }

    private static String getMachineIdByInetAddress() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

    private static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        System.out.println(MAX_MACHINE_NUM);
    }
}
