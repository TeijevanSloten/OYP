package com.ordina.email;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.NoSuchProviderException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {

    private Properties props;
    private Session session;

    public ReceiveMail() {
        setProperties();
        setSession();
    }

    private void setProperties() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    private void setSession() {
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMV.login , SMV.password);
                    }
                });
    }

    public ArrayList<Message> receiveMessages() {
        try {
            Store store = null;
            Folder folder = null;
            ArrayList<Message> email = new ArrayList<>();
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", SMV.email , SMV.password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message message[] = folder.getMessages();
            for (int i = 0; i < message.length; i++) {
                email.add(message[i]);
            }
            return email;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String getAttachments(Message[] message, int i) throws IOException, MessagingException {
        String attachments = "";

        Object objRef = message[i].getContent();
        Multipart multipart = null;

        if (objRef instanceof Multipart) {
            multipart = (Multipart) objRef;

            attachments += ("Amount of attachments: " + multipart.getCount() + "<br>");
            attachments += ("Attachment names: " + "<br>");
            for (int j = 0; j < multipart.getCount(); j++) {
                BodyPart bodyPart = multipart.getBodyPart(j);
                if (bodyPart.getFileName() != null) {
                    attachments += (bodyPart.getFileName()) + "<br>";
                }
            }
        }
        return attachments;
    }
}
