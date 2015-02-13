package com.ordina.email;

import com.ordina.entity.Email;
import com.ordina.session.AttachmentsFacade;
import com.ordina.session.EmailFacade;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class SendEmailWithAttachments {

    private HttpServletRequest request;
    private String subject;
    private List<FileItem> fileItemsList;
    private ArrayList<String> attachmentNames = new ArrayList<>();
    private String message = "";
    private String to = "";
    private String cc = "";
    private String bcc = "";
    private Email email = null;

    @EJB
    private EmailFacade ef;
    @EJB
    private AttachmentsFacade af;

    public SendEmailWithAttachments(HttpServletRequest request, String subject) throws Exception {
        this.request = request;
        this.subject = subject;
        sendEmail();
    }

    private void sendEmail() throws ServletException, Exception {
        SendEmail se = new SendEmail();
        checkIfRequestIsMultipartContent();
        createFileItemsList();
        loopThroughList();
        doIfReply();
        se.sendMessage(to, cc, bcc, message, attachmentNames.toArray(new String[attachmentNames.size()]), subject);
    }

    private void checkIfRequestIsMultipartContent() throws ServletException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
    }

    private void doIfReply() {
        if (email != null) {
            message += "\nOriginal message:\n" + email.getContent();
            if (to.equals("")) {
                to = email.getFromemail();
            }
        }
    }

    private void createFileItemsList() throws FileUploadException {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = new File("D:\\projecten\\Attachments\\temp\\");
        fileFactory.setRepository(filesDir);
        ServletFileUpload uploader = new ServletFileUpload(fileFactory);
        fileItemsList = uploader.parseRequest(request);
    }

    private void loopThroughList() throws Exception {
        for (FileItem fileItem : fileItemsList) {
            if (fileItem.isFormField()) {
                setAttachmentFields(fileItem);
            } else {
                seperateAttachments(fileItem);
            }
        }
    }

    private void setAttachmentFields(FileItem fileItem) {
        String fieldname = fileItem.getFieldName();
        String fieldvalue = fileItem.getString();
        if (fieldname.equals("to")) {
            to = fieldvalue;
        } else if (fieldname.equals("CC")) {
            cc = fieldvalue;
        } else if (fieldname.equals("BCC")) {
            bcc = fieldvalue;
        } else if (fieldname.equals("message")) {
            message = fieldvalue;
        } else if (fieldname.equals("subject")) {
            subject += fieldvalue;
        } else if (fieldname.equals("messageid")) {
            email = ef.findMessageId(Integer.parseInt(fieldvalue)).get(0);
        }

    }

    private void seperateAttachments(FileItem fileItem) throws Exception {
        if (!fileItem.getName().equals("")) {
            File file = new File("D:/projecten/Attachments/temp" + File.separator + fileItem.getName());
            fileItem.write(file);
            attachmentNames.add("D:/projecten/Attachments/temp" + File.separator + fileItem.getName());
        }
    }

}
