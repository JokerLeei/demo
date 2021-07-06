package com.demo.es.spring;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * https://www.cnblogs.com/wangzhuxing/p/9609127.html
 *
 * @author: lijiawei04
 * @date: 2021/7/2 7:08 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {

    @Resource(name = "elasticSearchClient")
    private RestHighLevelClient client;

    @Test
    public void test01() {
    }

}
