package com.example.demo.util.http;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * java原生http工具类，用于发送http请求
 *
 * @author: lijiawei04
 * @date: 2021/4/20 3:12 下午
 */
@Slf4j
public class HttpUtils {

    public static String post(String url, Integer readTimeout, Integer connectTimeout) {
        return post(url, null, readTimeout, connectTimeout, null);
    }

    public static String post(String url, JSON body, Integer readTimeout, Integer connectTimeout, Map<String, String> headers) {
        HttpURLConnection conn;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        String requestBody = null;
        StringBuilder sb = new StringBuilder();
        String line;
        String response = null;
        if (body != null) {
            requestBody = body.toJSONString();
        }
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.setUseCaches(false);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    conn.setRequestProperty(k, v);
                }
            }
            conn.connect();
            osw = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            if (requestBody != null) {
                osw.write(requestBody);
            }
            osw.flush();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            response = sb.toString();
        } catch (Exception e) {
            log.error("error post url:" + url, e);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error("error close conn", e);
            }
        }
        return response;
    }
}
