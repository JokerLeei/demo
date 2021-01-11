package com.example.demo.util.lottery;

import com.example.demo.algorithm.AliasMethod;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/1/8 3:37 下午
 */
@Slf4j
public class LotteryUtil {

    /**
     * 缓存已有的AliasMethod方法
     */
    private static final Cache<Map<Long, Double>, AliasMethod> ALIAS_METHOD_CACHE = CacheBuilder.newBuilder()
            .initialCapacity(200)
            .concurrencyLevel(4)
            .expireAfterAccess(24, TimeUnit.HOURS)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(200).recordStats()
            .build();

    /**
     * 获取抽奖结果
     *
     * @param probabilityMap 抽奖的概率的map key: 商品编号, value: 概率
     *
     * @return key 商品编号
     */
    public static Long lotteryResult(Map<Long, Double> probabilityMap) {

        TreeMap<Long, Double> treeMap = Maps.newTreeMap();
        treeMap.putAll(probabilityMap);

        List<Long> numberList = Lists.newArrayList(treeMap.keySet());
        AliasMethod cachedAliasMethod = ALIAS_METHOD_CACHE.getIfPresent(treeMap);
        if (Objects.nonNull(cachedAliasMethod)) {
            return numberList.get(cachedAliasMethod.next());
        }

        BigDecimal sum = null;
        // 先计算总和
        for (Double value : treeMap.values()) {
            BigDecimal decimal = BigDecimal.valueOf(value);
            if (sum == null) {
                sum = decimal;
            } else {
                sum = sum.add(decimal);
            }
        }

        List<Double> probabilities = Lists.newArrayListWithExpectedSize(treeMap.size());

        // 重新计算各部分的概率
        for (Map.Entry<Long, Double> entry : treeMap.entrySet()) {
            Number value = entry.getValue();
            BigDecimal decimal = BigDecimal.valueOf(value.doubleValue());
            BigDecimal result = decimal.divide(sum, 5, RoundingMode.HALF_UP);
            probabilities.add(result.doubleValue());
        }

        AliasMethod aliasMethod = AliasMethod.build(probabilities);

        log.info("各部分抽奖概率: map: {}, probabilities: {}", treeMap, probabilities);
        log.info("cache info: {}", ALIAS_METHOD_CACHE.stats());
        ALIAS_METHOD_CACHE.put(treeMap, aliasMethod);
        return numberList.get(aliasMethod.next());
    }

    public static void main(String[] args) {

        Map<Long, Double> map = new HashMap<>();
        map.put(1L, 0.1);
        map.put(2L, 0.2);
        map.put(3L, 0.3);
        map.put(4L, 0.4);

        Map<Long, AtomicInteger> resultMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            Long aLong = lotteryResult(map);
            if (!resultMap.containsKey(aLong)) {
                resultMap.put(aLong, new AtomicInteger());
            }
            resultMap.get(aLong).incrementAndGet();
        }

        resultMap.forEach((k, v) -> System.out.println(k + "==" + resultMap.get(k)));

    }

    public static void main2(String[] args)throws InterruptedException{
        Map map1 = new HashMap();  //定义Map集合对象
        map1.put("apple","新鲜的苹果");  //向集合中添加对象
        map1.put("computer","配置优良的计算机");
        map1.put("book","堆积成山的图书");
        Map map2 = new HashMap();
        map2.put("apple","新鲜的苹果");  //定义Map集合对象map2
        map2.put("computer","配置优良的计算机");
        map2.put("book","堆积成山的图书");
        boolean contains = map1.equals(map2);  //判断集合是否相等
        if(contains){
            System.out.println("两个Map对象相同");
        }else{
            System.out.println("这不是两个相同的Map对象");
        }
    }

}