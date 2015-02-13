package com.ordina.email;

import com.ordina.entity.Email;
import java.io.IOException;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

    private Session session = SMV.getSession();
    private Email replyto = null;

    public SendEmail() {
    }

    public SendEmail(Email replyto) {
        this.replyto = replyto;
    }

    public void sendMessage(String to, String cc, String bcc, String body, String[] attachFiles, String subject) {
        try {
            Message message = new MimeMessage(session);
            if (this.replyto != null) {
                message = (MimeMessage) message.reply(false);
                message.setFrom(new InternetAddress(replyto.getFromemail()));
                message.setReplyTo(new InternetAddress[]{new InternetAddress(replyto.getFromemail())});
            }
            message.setFrom(new InternetAddress(SMV.email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent(setEmailBody(body, attachFiles));
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Multipart setEmailBody(String body, String[] attachFiles) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart = addAttachments(attachFiles, multipart);
        return multipart;
    }

    private Multipart addAttachments(String[] attachFiles, Multipart multipart) throws MessagingException {
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
        }
        return multipart;
    }
}
