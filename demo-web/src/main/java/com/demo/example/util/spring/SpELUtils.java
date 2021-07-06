package com.demo.example.util.spring;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * SpEL工具类
 *
 * @author: lijiawei04
 * @date: 2020/12/23 10:42 上午
 */
public class SpELUtils {

    /**
     * 获取被拦截方法参数名列表(使用Spring支持类库)
     */
    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    /**
     * SPEl上下文解析器
     */
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * <h2>解析EL表达式</h2>
     *
     * @param args         方法参数
     * @param method       方法
     * @param elExpression EL表达式
     * @param resultType   结果类型
     * @param <T>          结果类型
     *
     * @return 解析结果
     */
    public static <T> T parseElExpression(Object[] args, Method method, String elExpression, Class<T> resultType) {
        String [] paraNameArr = PARAMETER_NAME_DISCOVERER.getParameterNames(method);

        StandardEvaluationContext elContext = new StandardEvaluationContext();
        // 把方法参数放入SPEl上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            Object paraValue = args[i];
            elContext.setVariable(paraNameArr[i], paraValue);
        }
        // 解析SPEl表达式
        return EXPRESSION_PARSER.parseExpression(elExpression).getValue(elContext, resultType);
    }

    public static void main(String[] args) {
        StandardEvaluationContext elContext = new StandardEvaluationContext();
        elContext.setVariable("param1", "abc");
        elContext.setVariable("param2", 111);

        String value = EXPRESSION_PARSER.parseExpression("#param1.concat(#param2)").getValue(elContext, String.class);

        System.out.println(value);
    }
}
