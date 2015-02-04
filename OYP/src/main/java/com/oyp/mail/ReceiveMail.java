package com.oyp.mail;

import java.io.IOException;
import javax.mail.NoSuchProviderException;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {

    private Properties props;
    private Session session;
    private Store store;

    public ReceiveMail() {
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
                        return new PasswordAuthentication("pipodekloun2003", "pipowachtwoord");
                    }
                });
    }

    public String showMessages() {
        String messages = "";
        try {
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", "pipodekloun2003@gmail.com", "pipowachtwoord");
            Folder folder = store.getFolder("INBOX");//get inbox
            folder.open(Folder.READ_ONLY);//open folder only to read
            Message message[] = folder.getMessages();
            for (int i = 0; i < message.length; i++) {
                messages += "-----------------------------------------------------------<br>";
                messages += message[i].getFrom()[0] + "<br>";
                messages += message[i].getSubject() + "<br>";
                messages += message[i].getContent().toString() + "<br><br>";
                messages += "-----------------------------------------------------------<br>";
            }
            folder.close(true);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
