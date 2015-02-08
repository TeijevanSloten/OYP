package com.ordina.email;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

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
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMV.login, SMV.password);
                    }
                });
        this.replyto = replyto;
    }

    public boolean sendMessage(String to, String body) {
        try {
            Message message = new MimeMessage(session);
            if (this.replyto != null) {
                message = (MimeMessage) message.reply(false);
                message.setFrom(new InternetAddress(replyto.getFrom()));
                message.setText("Replied message");
                message.setReplyTo(replyto.getMessage().getReplyTo());
            }
            message.setFrom(new InternetAddress(SMV.email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setText(body);

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
            message.setFrom(new InternetAddress(replyto.getFrom()));
            message.setText(body + "\n\n Original message:\n" +
                    replyto.getContent());
            message.setReplyTo(replyto.getMessage().getReplyTo());

            message.setSubject("reply");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyto.getFrom()));
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
            message.setFrom(new InternetAddress(replyto.getFrom()));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            
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
