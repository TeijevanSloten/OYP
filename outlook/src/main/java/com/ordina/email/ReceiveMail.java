package com.ordina.email;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {
    private Session session = SMV.getSession();

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
