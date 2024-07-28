/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class Email {

    public void sendSignUpEmail(String receiver) {
        final String from = "minhtrang12102003@gmail.com";
        final String password = "qcls znct qyla necu";

        //Properties : khai báo các thuộc tính của email
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587"); //TLS 587 SSL 465
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        //create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        // tạo ra 1 phiên làm việc
        Session session = Session.getInstance(prop, auth);
        //Tạo 1 tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            //set email subject field

            // Set the content of the email message
            msg.setSubject("Welcome to Furniture - Confirm Your Email");

            
            String emailContent
                    = "<div style=\"text-align: center; padding: 20px;\">"
                    + "  <h2>Welcome to Furniture!</h2>"
                    + "  <p>Thank you for joining our community of fashion enthusiasts.</p>"
                    + "  <p>Please click the link below to verify your email address:</p>"
                    + "  <a href=\"http://localhost:8080/FurnitureHieu/VerifyEmail?email=" + receiver + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Verify Your Email</a>"
                    + "  <p>If you did not sign up for a Furniture account, please disregard this email.</p>"
                    + "</div>";

            msg.setContent(emailContent, "text/html");
            
            
            
            try {
                Transport.send(msg);
                System.out.println("Email đã được gửi thành công!");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Đã xảy ra lỗi khi gửi email.");
            }

        } catch (Exception e) {
        }
    }
    
    public void sendResetEmail(String receiver){
            final String from = "minhtrang12102003@gmail.com";
        final String password = "qcls znct qyla necu";

        //Properties : khai báo các thuộc tính của email
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587"); //TLS 587 SSL 465
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        //create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        // tạo ra 1 phiên làm việc
        Session session = Session.getInstance(prop, auth);
        //Tạo 1 tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            //set email subject field

            // Set the content of the email message
            msg.setSubject("Welcome to Furniture - Confirm Your Email");
            String emailContent
                    = "<div style=\"text-align: center; padding: 20px;\">"
                    + "  <h2>Welcome to Furniture!</h2>"
                    + "  <p>Thank you for joining our community of fashion enthusiasts.</p>"
                    + "  <p>Please click the link below to verify your email address:</p>"
                    + "  <a href=\"http://localhost:8080/FurnitureHieu/VerifyResetEmail?email=" + receiver + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Verify Your Email</a>"
                    
                    + "</div>";

            msg.setContent(emailContent, "text/html");
            
            
            
            try {
                Transport.send(msg);
                System.out.println("Email đã được gửi thành công!");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Đã xảy ra lỗi khi gửi email.");
            }

        } catch (Exception e) {
        }
    }
    
    public void sendFeedbackEmail(String receiver, String action) {
        final String from = "minhtrang12102003@gmail.com";
        final String password = "qcls znct qyla necu";

        //Properties : khai báo các thuộc tính của email
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587"); //TLS 587 SSL 465
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        //create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        // tạo ra 1 phiên làm việc
        Session session = Session.getInstance(prop, auth);
        //Tạo 1 tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

            String subject = "";
            String emailContent = "";

            switch (action) {
                
                case "orderShipping":
                    subject = "Your Order is on the Way - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px;\">"
                            + "  <h2>Your Order is on the Way!</h2>"
                            + "  <p>Your order has been shipped and is on its way to you. You can expect delivery soon.</p>"
                            + "  <p>Thank you for shopping with us!</p>"
                            + "</div>";
                    break;
                case "orderCancellation":
                    subject = "Order Cancellation - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px;\">"
                            + "  <h2>Order Cancellation Notice</h2>"
                            + "  <p>We regret to inform you that your order has been canceled due to certain issues.</p>"
                            + "  <p>We apologize for any inconvenience caused and hope to serve you in the future.</p>"
                            + "</div>";
                    break;
                case "feedbackRequest":
                default:
                    subject = "Thank You for Your Purchase - Share Your Experience!";
                    emailContent = "<div style=\"text-align: center; padding: 20px;\">"
                            + "  <h2>Thank You for Your Purchase!</h2>"
                            + "  <p>We hope you are enjoying your new product from Furniture.</p>"
                            + "  <p>We would love to hear about your experience. Please take a moment to share your feedback:</p>"
                            + "  <a href=\"http://localhost:8080/FurnitureHieu/FeedbackServlet?email=" + receiver + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Give Feedback</a>"
                            + "</div>";
                    break;
            }

            msg.setSubject(subject);
            msg.setContent(emailContent, "text/html");

            Transport.send(msg);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("An error occurred while sending the email.");
        }
    }
}
