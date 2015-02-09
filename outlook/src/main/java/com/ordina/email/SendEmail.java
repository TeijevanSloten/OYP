package com.ordina.email;

import com.ordina.entity.Email;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

    private Properties props;
    private Session session;
    private Email replyto = null;

    public SendEmail() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMV.login, SMV.password);
                    }
                });
    }

    public SendEmail(Email replyto) {
        this();
        this.replyto = replyto;
    }

    public boolean sendMessage(String to, String body, String[] attachFiles) {
        try {
            Message message = new MimeMessage(session);
            if (this.replyto != null) {
                message = (MimeMessage) message.reply(false);
                message.setFrom(new InternetAddress(replyto.getFromemail()));
                message.setText("Replied message");
                message.setReplyTo(new InternetAddress[]{new InternetAddress(replyto.getFromemail())});
            }
            message.setFrom(new InternetAddress(SMV.email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

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
            message.setContent(multipart);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendReply(String body) {
        try {
            Message message = new MimeMessage(session);
            message = (MimeMessage) message.reply(false);
            message.setFrom(new InternetAddress(replyto.getFromemail()));
            message.setText(body + "\n\n Original message:\n"
                    + replyto.getContent());
            message.setReplyTo(new InternetAddress[]{new InternetAddress(replyto.getFromemail())});
            message.setSentDate(new Date());
            message.setSubject("reply");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyto.getFromemail()));
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendForward(String to, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setSubject("Fwd: " + replyto.getSubject());
            message.setFrom(new InternetAddress(replyto.getFromemail()));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            message.setText(body
                    + "\n\nOriginal message:\n\n"
                    + replyto.getContent());

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
