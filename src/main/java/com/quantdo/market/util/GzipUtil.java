package com.quantdo.market.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * socket 工具包
 * <br>创建日期：2018年4月21日 下午4:57:08 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * @author 苏宏斌
 * @since 1.0
 * @version 1.0
 */
public final class GzipUtil {

    private static final int BUFFER_SIZE = 1024;

    private GzipUtil() {
    }

    /**
     * gzip解压缩
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static String decompress(byte[] bytes) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             GZIPInputStream gis = new GZIPInputStream(byteArrayInputStream);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            int count;
            byte[] data = new byte[BUFFER_SIZE];
            while ((count = gis.read(data, 0, BUFFER_SIZE)) != -1) {
                bos.write(data, 0, count);
            }
            return new String(bos.toByteArray());
        }
    }

    /**
     * 压缩
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static byte[] compress(String msg)throws Exception {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPOutputStream gos = new GZIPOutputStream(bos);
                ByteArrayInputStream is = new ByteArrayInputStream(msg.getBytes());
        ) {
            int count;
            byte[] data = new byte[BUFFER_SIZE];
            while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            return bos.toByteArray();
        }
    }
}
