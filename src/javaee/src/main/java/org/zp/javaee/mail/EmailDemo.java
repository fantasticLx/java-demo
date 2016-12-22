/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.mail;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * @author zhanpgeng
 * @date 2016/12/22.
 */
public class EmailDemo {
    private static final String MAIL_SERVER_HOST = "smtp.163.com";
    private static final String USER = "xxxx";
    private static final String PASSWORD = "xxxx";
    private static final String MAIL_FROM = USER + "@163.com";
    private static final String MAIL_TO = USER + "@163.com";

    private static final String TYPE_TEXT = "text";
    private static final String TYPE_HTML = "html";

    public static void main(String[] args) throws Exception {
        EmailInfo info = new EmailInfo();
        info.setFrom(MAIL_FROM);
        info.setTo(MAIL_TO);
        info.setTitle("测试邮件");
        info.setType(TYPE_HTML);
        info.setContent(getTestData());
        sendEmail(info);
    }

    private static Multipart getTestData() {
        String htmlContent = "<h1>Hello</h1>" + "<p>显示图片<img src='cid:abc.jpg'>1.jpg</p>";
        MimeMultipart mm = new MimeMultipart();

        try {
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(htmlContent, "text/html;charset=UTF-8");

            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource("D:\\00_Temp\\代码的坏味道.png"));
            image.setDataHandler(dh);
            image.setContentID("abc.jpg");

            // 描述数据关系
            mm.addBodyPart(text);
            mm.addBodyPart(image);
            mm.setSubType("related");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return mm;
    }

    private static void sendEmail(EmailInfo info) throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.debug", "false");
        prop.setProperty("mail.host", MAIL_SERVER_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 1、创建session
        Session session = Session.getInstance(prop);

        // 2、通过session得到transport对象
        Transport ts = session.getTransport();

        // 3、连上邮件服务器
        ts.connect(MAIL_SERVER_HOST, USER, PASSWORD);

        // 4、创建邮件
        MimeMessage message = new MimeMessage(session);

        fillEmailHeader(message, info);

        if (StringUtils.equals(info.getType(), TYPE_TEXT)) {
            fillTextEmailBody(message, info);
        } else if (StringUtils.equals(info.getType(), TYPE_HTML)) {
            fillHtmlEmailBody(message, info);
        }

        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * 邮件消息头
     */
    private static boolean fillEmailHeader(MimeMessage message, EmailInfo info) throws MessagingException {
        // 邮件的发件人
        if (StringUtils.isNotBlank(info.getFrom())) {
            message.setFrom(new InternetAddress(info.getFrom()));
        } else {
            System.out.println("发件人不能为空");
            return false;
        }

        // 邮件的收件人
        if (StringUtils.isNotBlank(info.getFrom())) {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(info.getTo()));
        } else {
            System.out.println("收件人不能为空");
            return false;
        }

        // 邮件的抄送人
        if (StringUtils.isNotBlank(info.getCc())) {
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(info.getCc()));
        }

        // 邮件的密送人
        if (StringUtils.isNotBlank(info.getBcc())) {
            message.setRecipient(Message.RecipientType.BCC, new InternetAddress(info.getBcc()));
        }

        // 邮件的标题
        message.setSubject(info.getTitle());

        return true;
    }

    private static void fillTextEmailBody(MimeMessage message, EmailInfo info) throws MessagingException {
        message.setText(info.getText());
    }

    private static void fillHtmlEmailBody(MimeMessage message, EmailInfo info) throws MessagingException {
        message.setContent(info.getContent());
        message.saveChanges();
    }
}
