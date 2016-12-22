/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.javaee.mail;

import javax.mail.Message;
import javax.mail.Multipart;

/**
 * @author zhanpgeng
 * @date 2016/12/22.
 */
public class EmailInfo {
    private String from; // 邮件的发件人
    private String to; // 邮件的收件人
    private String cc; // 邮件的抄送人
    private String bcc; // 邮件的密送人
    private String title;
    private String type;
    private String text;
    private Multipart content;

    public Multipart getContent() {
        return content;
    }

    public void setContent(Multipart content) {
        this.content = content;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
