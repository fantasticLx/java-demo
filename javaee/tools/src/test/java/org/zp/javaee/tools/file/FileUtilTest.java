/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.tools.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Victor Zhang
 * @date 2017/1/18.
 */
public class FileUtilTest {
    @Test
    public void test_toBytes_and_toFile() throws IOException {
        String imagePath = FileUtil.getResourcesPath() + "/images/lion2.jpg";
        byte[] bytes = FileUtil.toBytes(new File(imagePath));
        Assert.assertNotNull(bytes);
        FileUtil.toFile(bytes, new File("d:\\newfile.jpg"));
    }
}
