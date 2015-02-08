package com.ordina.servlet;

import com.ordina.email.ReceiveMail;
import com.ordina.email.SendEmail;
import com.ordina.session.EmailFacade;
import java.io.IOException;
import javax.ejb.EJB;
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
        this.getServletContext().setAttribute("email", ef.findAll());
        // store category list in servlet context

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        if (userPath.equals("/sendemail")) {
        } else if (userPath.equals("/showallmail")) {
            getServletContext().setAttribute("messages", ReceiveMail.getEmails());
        } else if (userPath.equals("/retrievemail")) {
            new ReceiveMail().retrieveMessages();
            response.sendRedirect("showallmail");
        } else if (userPath.equals("/showmail")) {
            getServletContext().setAttribute("mail", ReceiveMail.getSpecificMail(Integer.parseInt(request.getParameter("id"))));
        } else if(userPath.equals("/send")){
            SendEmail se = new SendEmail();
            se.sendMessage(request.getParameter("to"), request.getParameter("message"));
            response.sendRedirect("");
        } else if(userPath.equals("/sendreply")){
            SendEmail se = new SendEmail(ReceiveMail.getSpecificMail(Integer.parseInt(request.getParameter("messageid"))));
            se.sendReply(request.getParameter("message"));
            response.sendRedirect("");
        } else if(userPath.equals("/sendforward")){
            SendEmail se = new SendEmail(ReceiveMail.getSpecificMail(Integer.parseInt(request.getParameter("messageid"))));
            se.sendForward(request.getParameter("to"), request.getParameter("message"));
            response.sendRedirect("");
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

}
