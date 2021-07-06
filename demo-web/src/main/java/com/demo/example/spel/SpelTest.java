package com.demo.example.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author: lijiawei04
 * @date: 2020/10/26 12:03 下午
 */
public class SpelTest {

    public static void main(String[] args) throws Exception {
        Calculation calculation = new Calculation();
        calculation.setNumber(5);

        StandardEvaluationContext context = new StandardEvaluationContext(calculation);
        context.setVariable("number", 5);

        ExpressionParser parser = new SpelExpressionParser();
        Integer number = parser.parseExpression("#number").getValue(context, Integer.class);
        System.out.println(number);

        System.out.println("12345678900".replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

    }

}
