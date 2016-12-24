/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.tools.file;

import java.io.File;

/**
 * @author victor zhang
 * @date 2016/12/23.
 */
public class FileUtil {
    public static String getRootPath() {
        return System.getProperty("user.dir");
    }

    public static String getCurrentPath(){
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return classpath.substring(1, classpath.indexOf("/target/classes/"));
    }

    public static String getResourcesPath(){
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return classpath.substring(1, classpath.indexOf("/target")) + "/src/main/resources";
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
