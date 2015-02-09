package com.ordina.servlet;

import com.ordina.email.ReceiveMail;
import com.ordina.email.SendEmail;
import com.ordina.entity.Email;
import com.ordina.session.EmailFacade;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/test",
    "/sendemail",
    "/showmail",
    "/showallmail",
    "/retrievemail",
    "/forward",
    "/reply",
    "/send",
    "/sendforward",
    "/sendreply"
})
public class ControllerServlet extends HttpServlet {

    @EJB
    private EmailFacade ef;

    public void init() throws ServletException {
        getServletContext().setAttribute("messages", ef.findAll());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception, IOException {
        String userPath = request.getServletPath();
        if (userPath.equals("/retrievemail")) {
            saveNewMessages();
            response.sendRedirect("");
        } else if (userPath.equals(
                "/showmail")) {
            getServletContext().setAttribute("mail", ef.findMessageId(
                    Integer.parseInt(request.getParameter("id"))).get(0));
        } else if (userPath.equals("/send")) {
            this.sendEmailWithAttachments(request);
            response.sendRedirect("");

            return;
        } else if (userPath.equals("/sendreply")) {
            sendEmailWithAttachments(request, "RE: ");
            response.sendRedirect("");
        } else if (userPath.equals("/sendforward")) {
            sendEmailWithAttachments(request, "FWD: ");
            response.sendRedirect("");
        } else if (userPath.equals(
                "/forward")) {
            getServletContext().setAttribute("mail",
                    ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
        } else if (userPath.equals(
                "/reply")) {
            getServletContext().setAttribute("mail",
                    ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view" + userPath + ".jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }

        response.setContentType(
                "text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void saveNewMessages() {
        ArrayList<Message> messages = new ReceiveMail().receiveMessages();
        for (Message message : messages) {
            try {
                //if (ef.findMessageId(message.getMessageNumber()).size() <= 0) {

                Email email = new Email();
                email.setMessageid(message.getMessageNumber());
                email.setSubject(message.getSubject());
                email.setContent(message.getContent().toString());
                email.setFromemail(message.getFrom()[0].toString());
                email.setDate(message.getReceivedDate());

                String contentType = message.getContentType();
                if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();

                    for (int i = 0; i < multiPart.getCount(); i++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            part.saveFile("d://projecten/Attachments/" + part.getFileName());
                        }
                    }

                }

            } catch (MessagingException | IOException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendEmailWithAttachments(HttpServletRequest request, String subject) throws ServletException, Exception {
        SendEmail se = new SendEmail();
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = new File("D:\\projecten\\outlookitemstemp\\");
        fileFactory.setRepository(filesDir);
        ServletFileUpload uploader = new ServletFileUpload(fileFactory);
        ArrayList<String> attachmentNames = new ArrayList<>();
        String message = "";
        String to = "";
        Email email = null;
        List<FileItem> fileItemsList = uploader.parseRequest(request);
        for (FileItem fileItem : fileItemsList) {
            if (fileItem.isFormField()) {
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
            } else {
                if (!fileItem.getName().equals("")) {
                    File file = new File("D:\\projecten\\outlookitemstemp" + File.separator + fileItem.getName());
                    fileItem.write(file);
                    attachmentNames.add("D:\\projecten\\outlookitemstemp" + File.separator + fileItem.getName());
                }
            }
        }
        if (email != null) {
            message += "\nOriginal message:\n" + email.getContent();
            if (to.equals("")) {
                to = email.getFromemail();
            }
        }
        se.sendMessage(to, message, attachmentNames.toArray(new String[attachmentNames.size()]), subject);
    }

    private void sendEmailWithAttachments(HttpServletRequest request) throws ServletException, Exception {
        sendEmailWithAttachments(request, "");
    }
}
