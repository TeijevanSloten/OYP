package com.oyp.mail;

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
    private static ArrayList<Email> emails = new ArrayList<>();
    ArrayList<BodyPart> attachments = new ArrayList<>();
   

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

    public void retrieveMessages() {
        try {
            this.connectAndOpenFolder();
        } catch (MessagingException ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Email> getEmails() {
        return new ArrayList<>(emails);
    }

    private void connectAndOpenFolder() throws NoSuchProviderException, MessagingException, Exception {
        Store store = null;
        Folder folder = null;
        try {
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", SMV.email , SMV.password);
            folder = store.getFolder("INBOX");//get inbox
            folder.open(Folder.READ_ONLY);//open folder only to read
            Message message[] = folder.getMessages();
            
            emails.clear();
            for (int i = 0; i < message.length; i++) {
                getAttachments(message, i);
                this.saveMail(message[i]);
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ReceiveMail.class.getName()).log(Level.SEVERE, null, ex);  
        } finally {
            folder.close(true);
            store.close();
        }
    }

    public static Email getSpecificMail(int id) {
        for (Email email : emails) {
            if (Integer.parseInt(email.getMessageNumber()) == id) {
                return email;
            }
        }
        return null;
    }

    private ArrayList<BodyPart> getAttachments(Message[] message, int i) throws IOException, MessagingException, Exception {
        
        Object objRef = message[i].getContent();
        Multipart multipart = null;

        if (objRef instanceof Multipart) {
            multipart = (Multipart) objRef;
            
            for (int j = 0; j < multipart.getCount(); j++) {
                BodyPart bodyPart = multipart.getBodyPart(j);
                    attachments.add(bodyPart);  
            }
        }
        return attachments;
    }
    
    private void saveMail(Message message) throws MessagingException, IOException{
        emails.add(new Email(String.valueOf(message.getMessageNumber()),
                        message.getFrom()[0].toString(),
                        message.getSubject(),
                        message.getContent().toString(),
                        message.getReceivedDate(),
                        message,
                        attachments
                        
                        ));

    }
}
