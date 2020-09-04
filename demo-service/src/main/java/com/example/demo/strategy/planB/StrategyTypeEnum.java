package com.example.demo.strategy.planB;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lijiawei04
 * @date: 2020-09-02 14:07
 * @description:
 */
@Getter
@AllArgsConstructor
public enum StrategyTypeEnum {

    A_STRATEGY(1),
    B_STRATEGY(2),
    ;

    private Integer type;

    public static Optional<StrategyTypeEnum> getByType(Integer type) {
        return Stream.of(values()).filter(typeEnum -> typeEnum.type.equals(type)).findAny();
    }
}
