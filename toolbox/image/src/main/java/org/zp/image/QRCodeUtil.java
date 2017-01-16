/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码工具类
 *
 * @author Victor Zhang
 * @date 2017/1/16.
 */
public class QRCodeUtil {
    private static final String filepath = "d:\\qrcode.png";

    public static String encode() {
        return null;
    }

    public static String decode(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        String retStr = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<>();
            hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
            retStr = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static void main(String[] args) {
        encode();
        System.out.println(decode(filepath));
    }
}
