/**
 * The Apache License 2.0 Copyright (c) 2016 Victor Zhang
 */
package org.zp.template.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.zp.template.velocity.VelocityDemo;

/**
 * @author zhanpgeng
 * @date 2016/12/22.
 */
public class EmailDemo {
    private static final String MAIL_SMTP_SERVER = "smtp.163.com";
    private static final String MAIL_POP3_SERVER = "pop3.163.com";
    private static final String USER = "xxxxx";
    private static final String PASSWORD = "3xxxxxe";
    private static final String MAIL_FROM = USER + "@163.com";
    private static final String MAIL_TO = USER + "@163.com";

    private static final String TYPE_TEXT = "text";
    private static final String TYPE_HTML = "html";

    public static void main(String[] args) throws Exception {
        /* 1.初始化 Velocity */
        Properties props = new Properties();
        try {
            props.load(VelocityDemo.class.getResourceAsStream("/template/velocity.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(props);

        /* 2.创建一个上下文对象 */
        VelocityContext context = new VelocityContext();

        /* 3.添加你的数据对象到上下文 */
        context.put("name", "Victor Zhang");

        /* 4.选择一个模板 */
        Template template = velocityEngine.getTemplate("template/email.vm");

        /* 5.将你的数据与模板合并，产生输出内容 */
        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        EmailInfoDTO info = new EmailInfoDTO();
        info.setFrom(MAIL_FROM);
        info.setTo(MAIL_TO);
        info.setTitle("测试邮件");
        info.setType(TYPE_HTML);

        info.setText(sw.toString());
        sendEmail(info);
        // receiveEmail();
    }

    private static void sendEmail(EmailInfoDTO info) throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.debug", "false");
        prop.setProperty("mail.host", MAIL_SMTP_SERVER);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 1、创建session
        Session session = Session.getInstance(prop);

        // 2、通过session得到transport对象
        Transport ts = session.getTransport();

        // 3、连上邮件服务器
        ts.connect(MAIL_SMTP_SERVER, USER, PASSWORD);

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
    private static boolean fillEmailHeader(MimeMessage message, EmailInfoDTO info)
                    throws MessagingException {
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

    private static void fillTextEmailBody(MimeMessage message, EmailInfoDTO info)
                    throws MessagingException {
        message.setText(info.getText());
    }

    private static void fillHtmlEmailBody(MimeMessage message, EmailInfoDTO info)
                    throws MessagingException {
        message.setContent(info.getText(), "text/html;charset=ISO-8859-1");
        message.setHeader("Content-Transfer-Encoding", "Base64");
        message.saveChanges();
    }

    private static void receiveEmail() throws MessagingException, IOException {
        // 创建一个有具体连接信息的Properties对象
        Properties prop = new Properties();
        prop.setProperty("mail.debug", "false");
        prop.setProperty("mail.store.protocol", "pop3");
        prop.setProperty("mail.pop3.host", MAIL_POP3_SERVER);

        // 1、创建session
        Session session = Session.getInstance(prop);

        // 2、通过session得到Store对象
        Store store = session.getStore();

        // 3、连上邮件服务器
        store.connect(MAIL_POP3_SERVER, USER, PASSWORD);

        // 4、获得邮箱内的邮件夹
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        // 获得邮件夹Folder内的所有邮件Message对象
        Message[] messages = folder.getMessages();

        for (int i = 0; i < messages.length; i++) {
            // for (int i = 0; i < 3; i++) {
            String subject = messages[i].getSubject();
            String from = (messages[i].getFrom()[0]).toString();

            if (StringUtils.equals("张鹏，南京某五百强公司的前同事工资翻倍了", subject)) {
                System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
                System.out.println("第 " + (i + 1) + "封邮件的发件人地址：" + from);
                System.out.println(
                                "第 " + (i + 1) + "封邮件的内容：\n" + messages[i].getContent().toString());
            }

        }

        // 5、关闭
        folder.close(false);
        store.close();
    }
}
