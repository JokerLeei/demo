package com.demo.example.java8.stream;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

/**
 * @author: lijiawei04
 * @date: 2020-06-12 17:14
 * @description:
 */
public class GroupingBy {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
                                        new Dish("beef", false, 700, Dish.Type.MEAT),
                                        new Dish("chicken", false, 400, Dish.Type.MEAT),
                                        new Dish("french fries", true, 530, Dish.Type.OTHER),
                                        new Dish("rice", true, 350, Dish.Type.OTHER),
                                        new Dish("season fruit", true, 120, Dish.Type.OTHER),
                                        new Dish("pizza", true, 550, Dish.Type.OTHER),
                                        new Dish("prawns", false, 300, Dish.Type.FISH),
                                        new Dish("salmon", false, 450, Dish.Type.FISH));

        // 双重groupingBy（先按类型分组，再在每个类型里按热量分组）
        Map<Dish.Type, Map<Dish.CaloriesLevelEnum, List<Dish>>> collect =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                            groupingBy(dish -> {
                                                if (dish.getCalories() < 0) {
                                                    return Dish.CaloriesLevelEnum.UNKNOWN;
                                                } else if (dish.getCalories() <= 400) {
                                                    return Dish.CaloriesLevelEnum.DIET;
                                                } else if (dish.getCalories() <= 700) {
                                                    return Dish.CaloriesLevelEnum.NORNAL;
                                                } else {
                                                    return Dish.CaloriesLevelEnum.FAT;
                                                }
                                            })
                                           )
                                );
        String s = JSONObject.toJSONString(collect);
        System.out.println(s);


        // 把上面的操作改成方法引用
        Map<Dish.Type, Map<Dish.CaloriesLevelEnum, List<Dish>>> collect1 =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                            groupingBy(Dish::getCaloriesLevelEnum)));
        String s1 = JSONObject.toJSONString(collect1);
        System.out.println(s1);


        // 按子组收集数据（map<类型, 数量>）
        Map<Dish.Type, Long> collect2 = menu.stream().collect(groupingBy(Dish::getType, counting()));
        String s2 = JSONObject.toJSONString(collect2);
        System.out.println(s2);


        // map<类型, Optional<最高热量的食物>>
        Map<Dish.Type, Optional<Dish>> collect3 = menu.stream().collect(groupingBy(Dish::getType,
                                                                                   maxBy(comparingInt(Dish::getCalories))));
        System.out.println(collect3);
        String s3 = JSONObject.toJSONString(collect3);
        System.out.println(s3);

        // map<类型, 最高热量的食物> (取出上面optional的值)
        Map<Dish.Type, Dish> collect4 = menu.stream().collect(groupingBy(Dish::getType,
                                                                         collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                                                                                           Optional::get)));
        String s4 = JSONObject.toJSONString(collect4);
        System.out.println(s4);


        // map<类型, 热量总和>
        Map<Dish.Type, Integer> collect5 = menu.stream().collect(groupingBy(Dish::getType,
                                                                            summingInt(Dish::getCalories)));
        String s5 = JSONObject.toJSONString(collect5);
        System.out.println(s5);


        // groupingBy后生成的 map<key, List<T>> 后，对 List<T> 再进行map()
        Map<Dish.Type, List<DishVO>> collect6 = menu.stream().collect(groupingBy(Dish::getType,
                                                                                 mapping(Dish::toVO, toList())));
        String s6 = JSONObject.toJSONString(collect6);
        System.out.println(s6);


        Map<Boolean, List<Dish>> collect7 = menu.stream().collect(groupingBy(Dish::isVegetarian));
        String s7 = JSONObject.toJSONString(collect7);
        System.out.println(s7);

        System.out.println("-----------------------------------------------------------------------------------------");


    }

    public boolean isPrime(int candidate) {
        return IntStream.range(2, candidate)
                .noneMatch(value -> candidate % value == 0);
    }

}
@Data
@Accessors(chain = true)
class Dish {

    private final String name;

    private final boolean vegetarian;

    private final int calories;

    private final Type type;

    enum Type {
        MEAT,
        FISH,
        OTHER
    }
    @AllArgsConstructor
    @Getter
    enum CaloriesLevelEnum {
        DIET(0, 400),
        NORNAL(400, 700),
        FAT(700,9999),
        UNKNOWN(-1, -1)
        ;

        private final Integer lowCalories;

        private final Integer highCalories;

        public static Optional<CaloriesLevelEnum> getByCalories(Integer calories) {
            return Stream.of(values()).filter(caloriesLevelEnum -> calories >= caloriesLevelEnum.getLowCalories() && calories < caloriesLevelEnum.getHighCalories()).findAny();
        }

        public static Integer getTotalHighCalories() {
            return Stream.of(values()).mapToInt(value -> value.highCalories).sum();
        }
    }
    public static Dish.CaloriesLevelEnum getCaloriesLevelEnum(Dish dish) {
        Optional<Dish.CaloriesLevelEnum> optional = Dish.CaloriesLevelEnum.getByCalories(dish.getCalories());
        return optional.orElse(Dish.CaloriesLevelEnum.UNKNOWN);
    }
    public static DishVO toVO(Dish dish) {
        return new DishVO(dish.getName());
    }
}
@Data
@AllArgsConstructor
class DishVO {
    private String name;
}
