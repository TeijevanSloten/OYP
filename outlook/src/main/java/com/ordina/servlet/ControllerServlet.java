package com.ordina.servlet;


import com.ordina.email.SaveEmailAndAttachments;
import com.ordina.email.SendEmailWithAttachments;
import com.ordina.entity.Addresses;
import com.ordina.session.AddressesFacade;
import com.ordina.session.AttachmentsFacade;
import com.ordina.session.EmailFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

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
    "/deleteaddress",
    "/address",
    "/exportaddresses",
    "/agenda"
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
                    showMailListAndSpecificEmailIfRequested(request);

                    break;
                }
                case ("/send"): {
                    new SendEmailWithAttachments(request, "");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/sendreply"): {
                    new SendEmailWithAttachments(request, "RE: ");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/sendforward"): {
                    new SendEmailWithAttachments(request, "FW: ");
                    response.sendRedirect("showmail");
                    return;
                }
                case ("/forward"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findByName());
                    getServletContext().setAttribute("mail",
                            ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
                    break;
                }
                case ("/reply"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findByName());
                    getServletContext().setAttribute("mail",
                            ef.findMessageId(Integer.parseInt(request.getParameter("id"))).get(0));
                    break;
                }
                case ("/sendemail"): {
                    setActions("compose");
                    getServletContext().setAttribute("addresses",
                            addressesf.findByName());
                    break;
                }
                case ("/addressbook"): {
                    setActions("addresses");
                    getServletContext().setAttribute("addresses",
                            addressesf.findByName());

                    break;
                }
                case ("/address"): {
                    createNewAddressInAddressbook(request);
                    response.sendRedirect("addressbook");
                    return;
                }
                case ("/deleteaddress"): {
                    addressesf.remove(addressesf.find(Integer.parseInt(request.getParameter("id"))));
                    response.sendRedirect("addressbook");

                }
                case ("/exportaddresses"): {

                }
                case ("/agenda"): {
                    setActions("agenda");
                    break;
                }
            }
            request.getRequestDispatcher("/WEB-INF/view/" + userPath + ".jsp").forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMailListAndSpecificEmailIfRequested(HttpServletRequest request) {
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
    }

    private void createNewAddressInAddressbook(HttpServletRequest request) {
        Addresses address = new Addresses();
        address.setEmail(request.getParameter("email"));
        address.setName(request.getParameter("fullname"));
        addressesf.create(address);
    }
    
        private void setActions(String option) {
        getServletContext().setAttribute("actions", option);
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
}

