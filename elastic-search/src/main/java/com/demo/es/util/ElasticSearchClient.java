package com.demo.es.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 连接 ES
 * https://www.cnblogs.com/wangzhuxing/p/9609127.html
 *
 * @author: lijiawei04
 * @date: 2021/7/5 7:56 下午
 */
public class ElasticSearchClient {

    public static RestHighLevelClient getClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

}
