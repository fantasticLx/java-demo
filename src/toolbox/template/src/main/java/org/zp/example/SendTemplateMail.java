/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.example;

import org.apache.velocity.VelocityContext;
import org.zp.javaee.mail.MailDTO;
import org.zp.javaee.mail.MailUtil;
import org.zp.tools.template.velocity.VelocityUtil;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Victor Zhang
 * @date 2016/12/23.
 */
public class SendTemplateMail {
    public static class Hyperlink {
        private String link;
        private String name;

        Hyperlink(String name, String link) {
            this.name = name;
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static void main(String[] args) throws MessagingException {
        VelocityContext context = new VelocityContext();
        context.put("name", "Victor Zhang");
        context.put("hint", "欢迎使用Velocity邮件模板：");

        // 直接传入一个对象
        context.put("date", new Date());

        // 传入一个Vector
        Hyperlink item1 = new Hyperlink("百度首页", "https://www.baidu.com");
        Hyperlink item2 = new Hyperlink("网易首页", "http://www.163.com/");
        List<Hyperlink> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        context.put("links", list);
        context.put("logo", "http://images.cnblogs.com/cnblogs_com/jingmoxukong/709053/o_%e6%94%bb%e5%9f%8e%e7%8b%ae2.png");

        MailDTO info = new MailDTO();
        info.setTo("xxxx@163.com"); // 收件人邮箱
        info.setSubject("测试html邮件"); // 邮件主题
        info.setType("html");
        info.setCharset("utf-8");

        info.setText(VelocityUtil.getMergeOutput(context, "template/mail.vm"));
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendEmail(info);
    }
}
