package com.oyp.mail;



import java.util.Date;
import javax.mail.Message;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.MessagingException;


public class Email {
    private String messageNumber;
    private String from;
    private String subject;
    private String content;
    private Date receivedDate;
    private Message message;
    private ArrayList<BodyPart> attachmentsList;
    
    public Email() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Email(String messageNumber, String from, String subject, String content, Date receivedDate, Message message, ArrayList<BodyPart> attachments) {
        this.messageNumber = messageNumber;
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.receivedDate = receivedDate;
        this.message = message;
        this.attachmentsList = attachments;
    }
    public ArrayList<String> getAttachmentsStringRepresentation(){
        ArrayList<String> attachmentsFileNames = new ArrayList<>();
        for (BodyPart attachment: attachmentsList)
            try {
                if (attachment.getFileName() != null) {
                    attachmentsFileNames.add(attachment.getFileName());
                    return attachmentsFileNames;
                }
            } catch (MessagingException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
       return null; 
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
