package com.ordina.servlet;

import com.ordina.email.ReceiveMail;
import com.ordina.email.SendEmail;
import com.ordina.entity.Email;
import com.ordina.session.EmailFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        
        if (userPath.equals("/sendemail")) {
        } else if (userPath.equals("/showallmail")) {
            
        } else if (userPath.equals("/retrievemail")) {
            ArrayList<Message> messages = new ReceiveMail().receiveMessages();
            for (Message message : messages) {
                try {
                    if(ef.findMessageId(message.getMessageNumber()).size() <= 0) {
                        Email email = new Email();
                        email.setMessageid(message.getMessageNumber());
                        email.setSubject(message.getSubject());
                        email.setContent(message.getContent().toString());
                        email.setFromemail(message.getFrom()[0].toString());
                        email.setDate(message.getReceivedDate());
                        ef.create(email);
                        getServletContext().setAttribute("messages", ef.findAll());
                    }
                } catch (MessagingException ex) {
                    Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.sendRedirect(response, "showallmail");
        } else if (userPath.equals("/showmail")) {
            getServletContext().setAttribute("mail", ef.findMessageId(
                    Integer.parseInt(request.getParameter("id"))).get(0));
        } else if(userPath.equals("/send")){
            SendEmail se = new SendEmail();
            se.sendMessage(request.getParameter("to"), request.getParameter("message"));
            response.sendRedirect("");
        } else if(userPath.equals("/sendreply")){
            SendEmail se = new SendEmail(ef.findMessageId(Integer.parseInt(request.getParameter("messageid"))).get(0));
            se.sendReply(request.getParameter("message"));
            response.sendRedirect("");
        } else if(userPath.equals("/sendforward")){
            SendEmail se = new SendEmail(ef.findMessageId(Integer.parseInt(request.getParameter("messageid"))).get(0));
            se.sendForward(request.getParameter("to"), request.getParameter("message"));
            response.sendRedirect("");
        } else if(userPath.equals("/forward")) {
           getServletContext().setAttribute("mail",  
                   ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
        } else if(userPath.equals("/reply")) {
           getServletContext().setAttribute("mail",  
                   ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
        }
        
        
        
        
        try {
            request.getRequestDispatcher("/WEB-INF/view" + userPath + ".jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
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
