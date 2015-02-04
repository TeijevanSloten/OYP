package com.sprhib.controller;

import com.oyp.mail.ReceiveMail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "/mail")
    public ModelAndView mailFunctions() {
        ModelAndView modelAndView = new ModelAndView("showmail");
        modelAndView.addObject("messages", new ReceiveMail().showMessages());
        return modelAndView;
    }
}
