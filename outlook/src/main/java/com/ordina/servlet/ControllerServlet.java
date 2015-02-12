package com.ordina.servlet;

import com.ordina.email.SaveEmailAndAttachments;
import com.ordina.email.SendEmail;
import com.ordina.entity.Addresses;
import com.ordina.entity.Email;
import com.ordina.session.AddressesFacade;
import com.ordina.session.AttachmentsFacade;
import com.ordina.session.EmailFacade;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "ControllerServlet", urlPatterns = {
    "/sendemail",
    "/showmail",
    "/retrievemail",
    "/forward",
    "/reply",
    "/send",
    "/sendforward",
    "/sendreply",
    "/download",
    "/addressbook",
    "/addressbook",
    "/address",
    "/exportaddresses"
})
public class ControllerServlet extends HttpServlet {

    @EJB
    private EmailFacade ef;
    @EJB
    private AttachmentsFacade af;
    @EJB
    private AddressesFacade addressesf;

    public void init() throws ServletException {
        getServletContext().setAttribute("messages", ef.findAllByDate());
        ef.findAllByDate();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            String userPath = request.getServletPath();
            setActions("default");
            switch (userPath) {
                case ("/retrievemail"): {
                    new SaveEmailAndAttachments(ef, af);
                    getServletContext().setAttribute("messages", ef.findAllByDate());
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/showmail"): {
                    if (request.getParameter("id") != null) {
                        setActions("viewemail");
                        getServletContext().setAttribute("mail", ef.findMessageId(
                                Integer.parseInt(request.getParameter("id"))).get(0));
                        getServletContext().setAttribute("attachments", af.findMessageId(
                                Integer.parseInt(request.getParameter("id"))));
                    } else {
                        getServletContext().removeAttribute("mail");
                        getServletContext().removeAttribute("attachments");
                    }
                    break;
                }
                case ("/send"): {
                    sendEmailWithAttachments(request, "");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/sendreply"): {
                    sendEmailWithAttachments(request, "RE: ");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/sendforward"): {
                    sendEmailWithAttachments(request, "FW: ");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/forward"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findAll());
                    getServletContext().setAttribute("mail",
                            ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
                    break;
                }
                case ("/reply"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findAll());
                    getServletContext().setAttribute("mail",
                            ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
                    break;
                }
                case ("/sendemail"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findAll());
                    break;
                }
                case ("/addressbook"): {
                    setActions("addresses");
                    getServletContext().setAttribute("addresses",
                            addressesf.findAll());
                    break;
                }
                case ("/address"): {
                    Addresses address = new Addresses();
                    address.setEmail(request.getParameter("email"));
                    address.setName(request.getParameter("fullname"));
                    addressesf.create(address);
                    response.sendRedirect("addressbook");
                    return;
                }
                case ("/exportaddresses"): {
                    try (PrintWriter out = response.getWriter()) {
                      // JsonObject obj = new JsonObject();
                               
                               out.println(wrapJsonAddresses(addressesf.findAll()));
                    }
                    return;
                }
            }
            request.getRequestDispatcher("/WEB-INF/view/" + userPath + ".jsp").forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if attachment servlet-specific error occurs
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

    private void setActions(String option) {
        getServletContext().setAttribute("actions", option);
    }

    private void sendEmailWithAttachments(HttpServletRequest request, String subject) throws ServletException, Exception {
        SendEmail se = new SendEmail();
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = new File("D:\\projecten\\Attachments\\temp\\");
        fileFactory.setRepository(filesDir);
        ServletFileUpload uploader = new ServletFileUpload(fileFactory);
        ArrayList<String> attachmentNames = new ArrayList<>();
        String message = "";
        String to = "";
        String cc = "";
        String bcc = "";
        Email email = null;
        List<FileItem> fileItemsList = uploader.parseRequest(request);
        for (FileItem fileItem : fileItemsList) {
            if (fileItem.isFormField()) {
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
            } else {
                if (!fileItem.getName().equals("")) {
                    File file = new File("D:/projecten/Attachments/temp" + File.separator + fileItem.getName());
                    fileItem.write(file);
                    attachmentNames.add("D:/projecten/Attachments/temp" + File.separator + fileItem.getName());
                }
            }
        }
        if (email != null) {
            message += "\nOriginal message:\n" + email.getContent();
            if (to.equals("")) {
                to = email.getFromemail();
            }
        }
        se.sendMessage(to, cc, bcc, message, attachmentNames.toArray(new String[attachmentNames.size()]), subject);
    }

    public static String wrapJsonAddresses(List<Addresses> addresses) {
        String json = "{\"addresses\":[\n";
        for (Addresses address : addresses) {
            json += "\t{\"fullname\":\"" + address.getName() + "\", \"email\":\"" + address.getEmail() + "\"},\n";
        }
        json = json.substring(0, json.length() - 2) + "\n]";
        return json;
    }
    
    

}
