/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.mail.smtp.SMTPMessage;
import java.io.IOException;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import main.Movie;
import payment.Receipt;

/**
 *Helper class for sending email
 */
public class Mailer {
   
    private final String host = "smtp.gmail.com";
    private final String port = "587";
    private final Properties props = System.getProperties();
    private final String FROM = "xrayrent@gmail.com";
    private final String PASSWORD = "abcdeedcba2424";
    private static Mailer mailer;
    
    private Mailer() {
        
    }
    
    private void sendEmail(String destinationEmail, String content) {
        
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", FROM);
	props.put("mail.smtp.password", PASSWORD);
	props.put("mail.smtp.port", port);
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
        
        MimeBodyPart p1 = new MimeBodyPart();
	Multipart mp = new MimeMultipart();
        
        try {
            mp.addBodyPart(p1);
            p1.setText(content);
            SMTPMessage message = new SMTPMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinationEmail));
            message.setContent(mp);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, FROM, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
	} catch (MessagingException ex) {
            ex.printStackTrace();
	}
        
        
    }
    
    public static Mailer getInstance() {
        if(mailer == null) {
            return new Mailer();
        }
        return mailer;
    }
    
    public void sendNewReleasesEmail(String destinationEmail,  List<Movie> newReleases) {
        //to be implemented
    }
    
    public void sendReceiptEmail(String destinationEmail, Receipt receipt) throws IOException {
        StringBuilder receiptSb = new StringBuilder();
        receiptSb.append("\t\t\tThank you for renting from us: here is your receipt.\n");
        receiptSb.append("\t\t\t*******************************************************\n");
        receiptSb.append("\t\t\t\t\t\t\tRECEIPT\n");
        receiptSb.append("\t\t\t*******************************************************\n");
        receipt.getItems().forEach((movie, rentalPrice) -> {
            receiptSb.append(String.format("\t\t\tITEM: %s\t\t\t\tCOST: $%5.2f\n", movie, rentalPrice));
        });
        
        sendEmail(destinationEmail, receiptSb.toString());
        
    }
    
    
}
