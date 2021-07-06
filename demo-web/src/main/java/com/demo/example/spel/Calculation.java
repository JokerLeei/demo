package com.demo.example.spel;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2020/10/26 12:02 下午
 */
@Data
public class Calculation {

    private int number;

    public int cube() {
        return number * number * number;
    }
}
