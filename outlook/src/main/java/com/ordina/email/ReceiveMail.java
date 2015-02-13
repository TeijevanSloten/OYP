package com.ordina.email;

import com.ordina.utillity.SMV;
import java.util.ArrayList;
import javax.mail.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {
    private Session session = SMV.getSession();
    private Store store = null;
    private Folder folder = null;

    public ArrayList<Message> receiveMessages() {
        try {
            ArrayList<Message> email = new ArrayList<>();
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", SMV.email, SMV.password);
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
}
