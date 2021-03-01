package com.example.demo.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * InputStream OutputStream 工具类
 *
 * @author: lijiawei04
 * @date: 2021/2/25 10:30 上午
 */
@Slf4j
public abstract class IOUtil {

    public static byte[] inputStream2ByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int length = 0;

        while(length != -1) {
            try {
                length = is.read(b);
            } catch (IOException e) {
                log.error("getByteByStream error when read from InputStream", e);
                throw new RuntimeException(e);
            }

            if (length != -1) {
                baos.write(b, 0, length);
            }
        }

        try {
            is.close();
        } catch (IOException e) {
            log.error("getByteByStream error when close InputStream", e);
        }

        return baos.toByteArray();
    }

}
