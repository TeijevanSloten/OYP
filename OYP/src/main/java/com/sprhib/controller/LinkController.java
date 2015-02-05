package com.sprhib.controller;

import com.oyp.mail.ReceiveMail;
import com.oyp.mail.SendEmail;
import javax.servlet.http.HttpServletRequest;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
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
    public ModelAndView getMailFunction() {
        ModelAndView modelAndView = new ModelAndView("showmail");
        ReceiveMail rm = new ReceiveMail();
        rm.retrieveMessages();
        modelAndView.addObject("messages", ReceiveMail.getEmails());
        return modelAndView;
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
}
