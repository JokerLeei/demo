package com.demo.example.filter;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * loggerId filter
 *
 * @author: lijiawei04
 * @date: 2021/2/3 8:52 下午
 */
public class LoggerIdFilter implements Filter {
    private String app;
    private Random generator = new Random();

    private static final String LOGGER_ID_PARAM_NAME = "logger_id";

    public LoggerIdFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.app = filterConfig.getInitParameter("app");
        if (this.app == null || "".equals(this.app.trim())) {
            this.app = UUID.randomUUID().toString();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 外部服务通过http调用本服务, 若header、requestParam里有logger_id则用传来的logger_id(跨服务日志排查)
        // 否则根据DigestUtils.md5Hex()生成一个随机logger_id
        String loggerId = ((HttpServletRequest)request).getHeader(LOGGER_ID_PARAM_NAME);
        if (loggerId == null || loggerId.trim().length() == 0) {
            loggerId = request.getParameter(LOGGER_ID_PARAM_NAME);
        }

        if (loggerId == null || loggerId.trim().length() == 0) {
            loggerId = DigestUtils.md5Hex(this.app + System.nanoTime() + this.generator.nextInt());
        }

        MDC.put(LOGGER_ID_PARAM_NAME, loggerId);
//        ((HttpServletResponse)response).setHeader(LOGGER_ID_PARAM_NAME, loggerId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(LOGGER_ID_PARAM_NAME);
        }

    }

    @Override
    public void destroy() {
        MDC.remove(LOGGER_ID_PARAM_NAME);
    }
}
