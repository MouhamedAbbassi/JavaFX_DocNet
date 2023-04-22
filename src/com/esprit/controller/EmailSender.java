/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author asus
 */
public class EmailSender {
    
    public static void sendEmail(String recipientEmail) throws MessagingException {
        String username = "nassim.benali@esprit.tn"; // your email address
        String password = ""; // your email password
       
        String subject ="updateOrdonnance";
        String message ="your ordonnance has been updated";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // or your mail server host name
        properties.put("mail.smtp.port", "587"); // or your mail server port

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        msg.setSubject(subject);
        msg.setText(message);

        Transport.send(msg);
    }
}
