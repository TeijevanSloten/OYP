package com.ordina.utillity;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SMV {
    
    public static final String email = "pipodekloun2003@gmail.com";
    public static final String password = "pipowachtwoord";
    public static final String login = "pipodekloun2003";
    
    private static Properties getProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }
    
    public static Session getSession(){ 
        Session session = Session.getInstance(SMV.getProperties(),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMV.login , SMV.password);
                    }
                });
        return session;
    }
    
}
