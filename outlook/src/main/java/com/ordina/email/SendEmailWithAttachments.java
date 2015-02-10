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
    private File filesDir;
    private List<FileItem> fileItemsList;
    private ArrayList<String> attachmentNames = new ArrayList<>();
    private String message = "";
    private String to = "teije.van.sloten@ordina.nl";
    private Email email = null;
    
    @EJB
    private EmailFacade ef;
    @EJB
    private AttachmentsFacade af;

  

    public SendEmailWithAttachments(HttpServletRequest request, String subject, EmailFacade ef, AttachmentsFacade af) throws Exception {
        this.request = request;
        this.subject = subject;
        this.ef = ef;
        this.af = af;
        sendEmail();
    }

    private void sendEmail() throws ServletException, Exception {
        SendEmail se = new SendEmail();
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }

        if (email != null) {
            message += "\nOriginal message:\n" + email.getContent();
            if (to.equals("")) {
                to = email.getFromemail();
            }
        }
        se.sendMessage(to, message, attachmentNames.toArray(new String[attachmentNames.size()]), subject);
    }

    private void createFileItemsList() throws FileUploadException {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        filesDir = new File("D:\\projecten\\Attachments\\temp\\");
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
            File file = new File("" + File.separator + fileItem.getName());
            fileItem.write(file);
            attachmentNames.add("" + File.separator + fileItem.getName());
        }
    }
}
