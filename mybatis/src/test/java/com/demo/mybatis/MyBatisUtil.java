package com.demo.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * MyBatis工具类
 *
 * @author: lijiawei04
 * @date: 2020/12/16 10:25 上午
 */
public class MyBatisUtil {

    public static final SqlSessionFactory SQL_SESSION_FACTORY;
    public static final ThreadLocal<SqlSession> SESSION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSession getCurrentSqlSession() {
        SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
        if (Objects.isNull(sqlSession)) {
            sqlSession = SQL_SESSION_FACTORY.openSession();
            SESSION_THREAD_LOCAL.set(sqlSession);
        }
        return sqlSession;
    }

    public static void closeCurrentSession() {
        SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
        if (Objects.nonNull(sqlSession)) {
            sqlSession.close();
        }
        SESSION_THREAD_LOCAL.set(null);
    }

}
