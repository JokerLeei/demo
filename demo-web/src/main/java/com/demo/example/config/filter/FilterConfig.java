package com.demo.example.config.filter;

import com.demo.example.filter.LoggerIdFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * spring 配置 filter
 *
 * @author: lijiawei04
 * @date: 2021/2/3 8:55 下午
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggerIdFilter> loggerIdFilter() {
        final FilterRegistrationBean<LoggerIdFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoggerIdFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("*");
        // FilterRegistrationBean中dispatcherType的定义与web.xml中的默认定义不同，这里统一修改为web.xml中的配置(request)，方便迁移
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }

}
