package com.oyp.mail;

import java.util.Date;

public class Email {
    private String messageNumber;
    private String from;
    private String subject;
    private String content;
    private Date receivedDate;
    
    public Email() {
    }

    public Email(String messageNumber, String from, String subject, String content, Date receivedDate) {
        this.messageNumber = messageNumber;
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.receivedDate = receivedDate;
    }

    public String getFrom() {
        return from;
    }

    public String getMessageNumber() {
        return messageNumber;
    }

    public String getReceivedDate() {
        return receivedDate.toString();
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    } 
}
