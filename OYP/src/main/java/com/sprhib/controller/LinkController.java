package com.sprhib.controller;

import com.oyp.mail.ReceiveMail;
import com.oyp.mail.SendEmail;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkController {
   
    @RequestMapping(value = "/")
    public ModelAndView mainPage() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/index")
    public ModelAndView indexPage() {
        return new ModelAndView("home");
    }
    
    @RequestMapping(value = "/showmail")
    public ModelAndView showMail() {
        ModelAndView modelAndView = new ModelAndView("showallmail");
        modelAndView.addObject("messages", ReceiveMail.getEmails());
        return modelAndView;
    }
     
    @RequestMapping(value = "/showmail/{id}")
    public ModelAndView getMailFunction(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("showmail");
        modelAndView.addObject("mail", ReceiveMail.getSpecificMail(id));
        return modelAndView;
    } 
    
    @RequestMapping(value = "/retrievemail")
    public String retrieveMail() {
        new ReceiveMail().retrieveMessages();
        return "redirect:/mail/showmail";
    }
    
    @RequestMapping(value = "/sendmail")
    public ModelAndView sendMailFunction() {
        ModelAndView modelAndView = new ModelAndView("sendmail");
        return modelAndView;
    }
    
    @RequestMapping(value = "/sendmail", method=RequestMethod.POST)
    public @ResponseBody ModelAndView handleMail(HttpServletRequest request) {
        request.getParameterNames();
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("response", new SendEmail().sendMessage(
                request.getParameter("to"), request.getParameter("message")));
        return modelAndView;
    }
    
    @RequestMapping(value = "/forward", method = RequestMethod.GET)
    public ModelAndView getForwardPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("forward");
        modelAndView.addObject("mail", ReceiveMail.getSpecificMail(
                Integer.parseInt(request.getParameter("id"))));
        return modelAndView;
    }
    @RequestMapping(value = "/reply", method = RequestMethod.GET)
    public ModelAndView getReplyPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("reply");
        modelAndView.addObject("mail", ReceiveMail.getSpecificMail(
                Integer.parseInt(request.getParameter("id"))));
        return modelAndView;
    }
    

}
