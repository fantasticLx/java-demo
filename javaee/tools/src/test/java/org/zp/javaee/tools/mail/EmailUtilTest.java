/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.tools.mail;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Victor Zhang
 * @date 2016/12/23.
 * @see MailUtil
 */
public class EmailUtilTest {
    private final String MAIL_TO = "xxxxxxx@163.com"; // 收件人邮箱
    private static MailUtil emailUtil;

    @BeforeClass
    public static void init() {
        emailUtil = new MailUtil();
    }

    @Test
    public void sendTextEmail() {
        MailDTO info = new MailDTO();
        info.setTo(MAIL_TO);
        info.setSubject("测试text邮件");
        info.setType("text");
        info.setText("对酒当歌，人生几何，譬如朝露，去日苦多。");
        try {
            emailUtil.sendEmail(info);
        } catch (MessagingException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void sendHtmlEmail() {
        String htmlContent = "<h1>测试内容为html的邮件</h1>" + "<p><h2>显示图片</h2><br><img src='cid:lion.jpg'></p>";
        MimeMultipart mm = new MimeMultipart();

        try {
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(htmlContent, "text/html;charset=UTF-8");

            MimeBodyPart image = new MimeBodyPart();
            String imgPath = System.getProperty("user.dir") + "/src/main/resources/images/lion.jpg";
            DataHandler dh = new DataHandler(new FileDataSource(imgPath));
            image.setDataHandler(dh);
            image.setContentID("lion.jpg");

            // 描述数据关系
            mm.addBodyPart(text);
            mm.addBodyPart(image);

            MailDTO info = new MailDTO();
            info.setTo(MAIL_TO);
            info.setSubject("测试html邮件");
            info.setType("html");
            info.setContent(mm);
            emailUtil.sendEmail(info);
        } catch (MessagingException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testHtmlEmailWithAttachment() {
        MimeMultipart mm = new MimeMultipart();

        try {
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("邮件中有两个附件。", "text/html;charset=UTF-8");
            mm.addBodyPart(text);

            // 添加邮件附件
            String imgPath = System.getProperty("user.dir") + "/src/main/resources/images/lion.jpg";
            String imgPath2 = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
            String[] files = {imgPath, imgPath2};
            for (String filename : files) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(filename);
                mm.addBodyPart(attachPart);
            }

            MailDTO info = new MailDTO();
            info.setTo(MAIL_TO);
            info.setSubject("测试带附件邮件");
            info.setType("html");
            info.setContent(mm);
            emailUtil.sendEmail(info);
        } catch (MessagingException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testReceiveEmail() {
        try {
            emailUtil.receiveEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
