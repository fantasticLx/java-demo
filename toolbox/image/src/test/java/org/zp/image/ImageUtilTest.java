/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image;

import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.junit.Assert;
import org.junit.Test;
import org.zp.image.dto.ImageParamDTO;
import org.zp.javaee.tools.file.FileUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * @author Victor Zhang
 * @date 2017/1/16.
 */
public class ImageUtilTest {
    @Test
    public void testToFile() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        final String newFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2_watermark";
        final String warterFile = System.getProperty("user.dir") + "/src/main/resources/images/wartermark.png";

        ImageParamDTO params = new ImageParamDTO();
        params.setFormat("png");
        params.setWaterMark(new ImageParamDTO.WaterMark(10, warterFile, 0.6f));
        ImageUtil.toFile(oldFile, newFile, params);
    }

    @Test
    public void testToBufferedImage() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";

        ImageParamDTO params = new ImageParamDTO();
        params.setWidth(64);
        params.setHeight(64);

        BufferedImage bufferedImage = ImageUtil.toBufferedImage(oldFile, params);
        Assert.assertNotNull(bufferedImage);
    }

    @Test
    public void testGetContentType() throws MagicParseException, MagicException, MagicMatchNotFoundException, IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        byte[] bytes = FileUtil.toBytes(new File(oldFile));
        String type = ImageUtil.getContentType(bytes);
        Assert.assertEquals("image/jpeg", type);
    }
}
