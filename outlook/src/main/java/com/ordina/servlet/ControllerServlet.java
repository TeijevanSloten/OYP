package com.ordina.servlet;

import com.ordina.email.ReceiveMail;
import com.ordina.email.SendEmail;
import com.ordina.entity.Email;
import com.ordina.session.EmailFacade;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.BodyPart;
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
import org.apache.commons.fileupload.FileUploadException;
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
            throws ServletException {
        String userPath = request.getServletPath();

        if (userPath.equals("/sendemail")) {
        } else if (userPath.equals("/showallmail")) {

        } else if (userPath.equals("/retrievemail")) {
            try {
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
                                        part.saveFile("c://Attachments/" + part.getFileName());
                                    }
                                }

                            }

                    } catch (MessagingException | IOException ex) {
                        Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.sendRedirect(response, "showallmail");
            } catch (IOException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (userPath.equals(
                "/showmail")) {
            getServletContext().setAttribute("mail", ef.findMessageId(
                    Integer.parseInt(request.getParameter("id"))).get(0));
        } else if (userPath.equals(
                "/send")) {
            SendEmail se = new SendEmail();

            if (!ServletFileUpload.isMultipartContent(request)) {
                throw new ServletException("Content type is not multipart/form-data");
            }
            DiskFileItemFactory fileFactory = new DiskFileItemFactory();
            File filesDir = new File("D:\\projecten\\");
            fileFactory.setRepository(filesDir);
            ServletFileUpload uploader = new ServletFileUpload(fileFactory);
            try {
                List<FileItem> fileItemsList = uploader.parseRequest(request);
                Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
                while (fileItemsIterator.hasNext()) {
                    FileItem fileItem = fileItemsIterator.next();
                    System.out.println("FieldName=" + fileItem.getFieldName());
                    System.out.println("FileName=" + fileItem.getName());
                    System.out.println("ContentType=" + fileItem.getContentType());
                    System.out.println("Size in bytes=" + fileItem.getSize());

                    File file = new File("D:\\projecten\\" + File.separator + fileItem.getName());
                    System.out.println("Absolute Path at server=" + file.getAbsolutePath());
                    try {
                        fileItem.write(file);
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("File " + fileItem.getName() + " uploaded successfully.");

                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

            try {
                //se.sendMessage(request.getParameter("to"), request.getParameter("message"));
                response.sendRedirect("");
            } catch (IOException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        } else if (userPath.equals(
                "/sendreply")) {
            SendEmail se = new SendEmail(ef.findMessageId(Integer.parseInt(request.getParameter("messageid"))).get(0));
            se.sendReply(request.getParameter("message"));
            try {
                response.sendRedirect("");
            } catch (IOException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (userPath.equals(
                "/sendforward")) {
            SendEmail se = new SendEmail(ef.findMessageId(Integer.parseInt(request.getParameter("messageid"))).get(0));
            se.sendForward(request.getParameter("to"), request.getParameter("message"));
            try {
                response.sendRedirect("");
            } catch (IOException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        } catch (Exception ex) {
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void sendRedirect(HttpServletResponse response, String url) throws IOException {
        response.sendRedirect(url);
        return;
    }
}
