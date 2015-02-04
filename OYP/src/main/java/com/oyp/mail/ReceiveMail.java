package com.oyp.mail;

import java.io.IOException;
import javax.mail.NoSuchProviderException;
import java.util.Properties;
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
    private Store store;
    private Folder folder;

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
                        return new PasswordAuthentication("pipodekloun2003", "pipowachtwoord");
                    }
                });
    }

    public String showMessages() {
        String messages = "";
        try {
            messages += buildMessage(connectAndOpenFolder());                      
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    private Message[] connectAndOpenFolder() throws NoSuchProviderException, MessagingException{
        
        store = session.getStore("imaps");
            store.connect("imap.gmail.com", "pipodekloun2003@gmail.com", "pipowachtwoord");
            folder = store.getFolder("INBOX");//get inbox
            folder.open(Folder.READ_ONLY);//open folder only to read
            Message message[] = folder.getMessages();
            return message;
    }
    
    public void disconnectAndCloseFolder() throws MessagingException {
        folder.close(true);
        store.close();
    }

    public String buildMessage(Message[] message) throws MessagingException, IOException {
        String messages = "";
            for (int i = 0; i < message.length; i++) {
                messages += "-----------------------------------------------------------<br>";
                messages += message[i].getFrom()[0] + "<br>";
                messages += message[i].getSubject() + "<br>";
                messages += message[i].getContent().toString() + "<br><br>";
                messages += getAttachments(message, i);
            }
        return messages;
    }

    private String getAttachments(Message[] message, int i) throws IOException, MessagingException {
        String attachments = "";

            Object objRef = message[i].getContent();
            Multipart multipart = null;

            if (objRef instanceof Multipart) {
                multipart = (Multipart) objRef;

                attachments += ("Amount of attachments: " + multipart.getCount() + "<br>");
                attachments += ("Attachment names: " + "<br>");
                //get and print the attachment names
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
