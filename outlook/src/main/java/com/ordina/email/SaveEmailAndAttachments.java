package com.ordina.email;

import com.ordina.entity.Attachments;
import com.ordina.entity.Email;
import com.ordina.servlet.ControllerServlet;
import com.ordina.session.AttachmentsFacade;
import com.ordina.session.EmailFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

public class SaveEmailAndAttachments {

    private EmailFacade ef;
    private AttachmentsFacade af;
    private ArrayList<Message> messages;

    public SaveEmailAndAttachments(EmailFacade ef, AttachmentsFacade af) {
        this.ef = ef;
        this.af = af;
        saveNewMessages();
    }

    private void saveNewMessages() {
        messages = new ReceiveMail().receiveMessages();
        for (Message message : messages) {
            if (ef.findMessageId(message.getMessageNumber()).size() <= 0) {
                try {
                    buildEmail(message);
                } catch (MessagingException | IOException e) {
                    Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    private void buildEmail(Message message) throws MessagingException, IOException {
        Email email = new Email();
        email.setMessageid(message.getMessageNumber());
        email.setSubject(message.getSubject());
        try{
            email.setContent(getStringContentEmail(message));
        }catch (MessagingException e){
            e.printStackTrace();
        }
        email.setFromemail(message.getFrom()[0].toString());
        email.setDate(message.getReceivedDate());
        ef.create(email);
        checkForAndLoopThroughAttachments(message, email);
    }

    String getStringContentEmail(Message message) throws IOException, MessagingException {
        String s = "This email has no content...";
        String contentType = message.getContentType();
        
        if (contentType.contains("multipart")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/*")) {
                    s = (String) bodyPart.getContent().toString();
                }
            }
        } else {
            s = message.getContent().toString();
        }
        return s;
    }

    private void checkForAndLoopThroughAttachments(Message message, Email email) throws MessagingException, IOException {
        String contentType = message.getContentType();
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                attachmentToDiskAndDatabase(email, part);
            }
        }
    }

    private void attachmentToDiskAndDatabase(Email email, MimeBodyPart part) throws MessagingException, IOException {
        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
            Attachments attachment = new Attachments();
            attachment.setContenttype(part.getContentType());
            attachment.setFilename(part.getFileName());
            attachment.setFilesize(String.valueOf(part.getSize()));
            attachment.setEmailid(email.getMessageid());
            af.create(attachment);
            part.saveFile("D:\\projecten\\Attachments\\" + email.getMessageid() + "-" + part.getFileName());
        }
    }
}
