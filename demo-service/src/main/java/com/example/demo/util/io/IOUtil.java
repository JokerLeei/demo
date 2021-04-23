package com.example.demo.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * InputStream OutputStream 工具类
 *
 * @author: lijiawei04
 * @date: 2021/2/25 10:30 上午
 */
@Slf4j
public abstract class IOUtil {

    /**
     * inputStream => byte[]
     */
    public static byte[] getByteArray(InputStream is) {
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

    /**
     * inputStream => outputStream
     */
    public ByteArrayOutputStream parse(InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    /**
     * outputStream => inputStream
     */
    public ByteArrayInputStream parse(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * inputStream => String
     */
    public String parseString(InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream.toString();
    }

    /**
     * OutputStream => String
     */
    public String parseString(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }

    /**
     * String => inputStream
     */
    public ByteArrayInputStream parseInputStream(String in) {
        return new ByteArrayInputStream(in.getBytes());
    }

    /**
     * String => outputStream
     */
    public ByteArrayOutputStream parseOutputStream(String in) throws Exception {
        return parse(parseInputStream(in));
    }

}
