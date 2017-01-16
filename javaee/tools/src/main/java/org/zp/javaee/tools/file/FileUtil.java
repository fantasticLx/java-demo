/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.tools.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author victor zhang
 * @date 2016/12/23.
 */
public class FileUtil {
    public static String getRootPath() {
        return System.getProperty("user.dir");
    }

    public static String getCurrentPath() {
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return classpath.substring(1, classpath.indexOf("/target/classes/"));
    }

    public static String getResourcesPath() {
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return classpath.substring(1, classpath.indexOf("/target")) + "/src/main/resources";
    }

    /**
     * 读取文件字节内容，可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getRootPath());
        System.out.println(getCurrentPath());
        System.out.println(getResourcesPath());

        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        System.out.println(FileUtil.class.getClassLoader().getResource("").getPath());
        System.out.println(Class.class.getClass().getResource("/").getPath());

        File f = new File(FileUtil.class.getResource("").getPath());
        System.out.println(f);
    }

    public static String getClassPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
}
