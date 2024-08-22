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

    public void sendResetEmail(String receiver) {
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

    public void sendMessageEmail(String receiver, String action, int order_id) {
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
                case "orderCanceledByCustomer":
                    subject = "Xác nhận hủy đơn hàng - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2>Đơn hàng của bạn đã được hủy thành công</h2>\n"
                            + "    <p>Xin chào,</p>\n"
                            + "    <p>Chúng tôi rất tiếc khi nhận được thông báo hủy đơn hàng của bạn. Đơn hàng của bạn đã được hủy thành công theo yêu cầu.</p>\n"
                            + "    <p>Chúng tôi rất tiếc khi không thể phục vụ bạn lần này. Nếu có bất kỳ lý do nào khiến bạn hủy đơn hàng, chúng tôi rất muốn biết để có thể cải thiện dịch vụ của mình. Đừng ngần ngại liên hệ với chúng tôi để cung cấp phản hồi của bạn.</p>\n"
                            + "    <p>Hy vọng sẽ có cơ hội phục vụ bạn trong tương lai gần. Cảm ơn bạn đã quan tâm đến sản phẩm của chúng tôi.</p>\n"
                            + "    <p>Trân trọng,</p>\n"
                            + "</div>";
                    break;

                case "orderConfirmed":
                    subject = "Xác nhận đơn hàng - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2>Đơn hàng của bạn đã được xác nhận thành công</h2>\n"
                            + "    <p>Xin chào,</p>\n"
                            + "    <p>Chúng tôi rất vui mừng thông báo rằng đơn hàng của bạn đã được xác nhận thành công.</p>\n"
                            + "    <p>Đơn hàng của bạn sẽ sớm được vận chuyển. Bạn có thể xem chi tiết đơn hàng để theo dõi trạng thái và thông tin vận chuyển của mình.</p>\n"
                            + "    <a href=\"http://localhost:8080/FurnitureHieu/MyOrderInformationServlet?id=" + order_id + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Xem chi tiết đơn hàng</a>"
                            + "    <p>Cảm ơn bạn đã tin tưởng và lựa chọn sản phẩm của chúng tôi. Chúng tôi hy vọng bạn sẽ hài lòng với sản phẩm của mình.</p>\n"
                            + "    <p>Trân trọng,</p>\n"
                            + "</div>";
                    break;

                case "feedbackRequest":
                    subject = "Cảm ơn bạn đã mua hàng - Chia sẻ trải nghiệm của bạn!";
                    emailContent = "<div style=\"text-align: center; padding: 20px;\">"
                            + " <h2>Cảm ơn bạn đã mua hàng!</h2>"
                            + " <p>Chúng tôi hy vọng bạn thích sản phẩm mới của Furniture.</p>"
                            + " <p>Chúng tôi rất muốn nghe về trải nghiệm của bạn. Vui lòng dành chút thời gian để chia sẻ phản hồi của bạn:</p>"
                            + "  <a href=\"http://localhost:8080/FurnitureHieu/FeedbackServlet?order_id=" + order_id + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Đánh giá đơn hàng</a>"
                            + "</div>";
                    break;

                case "deliveryConfirmation":
                    subject = "Đơn hàng của bạn đã được giao thành công - Xác nhận nhận hàng!";
                    emailContent = "<div style=\"text-align: center; padding: 20px;\">"
                            + " <h2>Đơn hàng của bạn đã được giao thành công!</h2>"
                            + " <p>Chúng tôi rất vui khi thông báo rằng đơn hàng của bạn đã được giao thành công.</p>"
                            + " <p>Vui lòng xác nhận rằng bạn đã nhận được đơn hàng bằng cách nhấn vào liên kết dưới đây:</p>"
                            + " <a href=\"http://localhost:8080/FurnitureHieu/MyOrderInformationServlet?id=" + order_id + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Xem chi tiết đơn hàng</a>"
                            + " <p>Nếu bạn gặp bất kỳ vấn đề nào với đơn hàng, xin vui lòng liên hệ với chúng tôi để được hỗ trợ.</p>"
                            + " <p>Cảm ơn bạn đã mua sắm với chúng tôi!</p>"
                            + "</div>";
                    break;

                case "payOrder":
                    subject = "Thanh toán đơn hàng!";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2 style=\"color: #d9534f;\">Thông Báo Thanh Toán Đơn Hàng</h2>\n"
                            + "    <p>Xin chào,</p>\n"
                            + "    <p>Chúng tôi muốn thông báo rằng đơn hàng của bạn sẽ bị hủy nếu không được thanh toán trong vòng 12 tiếng kể từ khi bạn nhận được thông báo này. Để đảm bảo rằng đơn hàng của bạn được giữ, vui lòng hoàn tất thanh toán trong thời gian quy định.</p>\n"
                            + "    <p>Để thanh toán và xem chi tiết đơn hàng, vui lòng nhấp vào đường link bên dưới:</p>\n"
                            + " <a href=\"http://localhost:8080/FurnitureHieu/MyOrderInformationServlet?id=" + order_id + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Xem chi tiết đơn hàng</a>"
                            + "    <p>Chúng tôi cảm ơn bạn đã lựa chọn dịch vụ của chúng tôi và mong được phục vụ bạn sớm.</p>\n"
                            + "    <p>Trân trọng,</p>\n"
                            + "    <p><strong>Đội ngũ Hỗ Trợ Khách Hàng</strong></p>\n"
                            + "</div>";
                    break;

                case "orderPlaced":
                    subject = "Đơn hàng của bạn đã được đặt thành công - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2>Đơn hàng của bạn đã được đặt thành công!</h2>\n"
                            + "    <p>Xin chào,</p>\n"
                            + "    <p>Chúng tôi vui mừng thông báo rằng đơn hàng của bạn đã được đặt thành công. Cảm ơn bạn đã lựa chọn sản phẩm của chúng tôi!</p>\n"
                            + "    <p>Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất và bạn sẽ nhận được thông báo khi đơn hàng của bạn được xác nhận. Để xem chi tiết đơn hàng vui lòng ấn xem chi tiết đơn hàng ở dưới.</p>\n"
                            + " <a href=\"http://localhost:8080/FurnitureHieu/MyOrderInformationServlet?id=" + order_id + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #337ab7; color: white; text-decoration: none; border-radius: 5px;\">Xem chi tiết đơn hàng</a>"
                            + "    <p>Cảm ơn bạn đã mua sắm với chúng tôi và mong được phục vụ bạn lần nữa!</p>\n"
                            + "    <p>Trân trọng,</p>\n"
                            + "</div>";
                    break;

                default:
            }

            msg.setSubject(subject);
            msg.setContent(emailContent, "text/html ; charset=utf-8");

            Transport.send(msg);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("An error occurred while sending the email.");
        }
    }

    public void sendSaleEmail(String receiver, String action, int order_id, String name, String email, String phone) {
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
                case "saleCanceledOrder":
                    subject = "Đơn hàng của bạn đã bị hủy - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2 style=\"color: #d9534f;\">Thông Báo Hủy Đơn Hàng</h2>\n"
                            + "    <p>Chúng tôi rất tiếc phải thông báo rằng đơn hàng của bạn đã bị hủy do một số lý do. Chúng tôi xin lỗi vì sự bất tiện này.</p>\n"
                            + "    <p>Bạn sẽ được hoàn 100% số tiền của đơn hàng. Để được hỗ trợ và hoàn trả lại số tiền, vui lòng liên hệ với bộ phận chăm sóc khách hàng của chúng tôi:</p>\n"
                            + "    <div style=\"margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; max-width: 500px; text-align: left;\">\n"
                            + "        <p><strong>Tên Sale:</strong>" + name
                            + "        <p><strong>Email:</strong>" + email
                            + "        <p><strong>Số Điện Thoại:</strong> " + phone
                            + "    </div>\n"
                            + "    <p>Chúng tôi xin lỗi vì sự bất tiện này và rất mong được phục vụ bạn trong các lần mua sắm tiếp theo. Cảm ơn bạn đã chọn sản phẩm của chúng tôi!</p>\n"
                            + "</div>";
                    break;

                case "orderShipping":
                    subject = "Đơn hàng của bạn đang trên đường giao - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2>Đơn hàng của bạn đang trên đường giao!</h2>\n"
                            + "    <p>Đơn hàng của bạn đã được chuyển đi và đang trên đường đến với bạn. Bạn vui lòng để ý điện thoại để bên vận chuyển có thể liên hệ với bạn sớm.</p>\n"
                            + "    <p>Bạn sẽ sớm nhận được hàng trong vòng 1-2 ngày tới. Vui lòng để ý điện thoại của mình để nhận hàng sớm nhất có thể. Nếu bạn muốn hoàn đơn hàng hoặc không muốn nhận hàng với lý do không phải do shop hoặc lỗi sản phẩm vui lòng thanh toán 10% số tiền đơn hàng.</p>\n"
                            + "    <p>Để được hỗ trợ hoặc có bất kỳ câu hỏi nào về vấn đề giao hàng, vui lòng liên hệ với bên giao hàng của chúng tôi:</p>\n"
                            + "    <div style=\"margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; max-width: 500px; text-align: left;\">\n"
                            + "        <p><strong>Nhân viên giao hàng:</strong> " + name + "</p>\n"
                            + "        <p><strong>Email:</strong> " + email + "</p>\n"
                            + "        <p><strong>Số Điện Thoại:</strong> " + phone + "</p>\n"
                            + "    </div>\n"
                            + "    <p>Cảm ơn bạn đã mua sắm với chúng tôi!</p>\n"
                            + "</div>";
                    break;
                
                case "deliveryFailedByCustomer":
                    subject = "Đơn hàng của bạn đã bị hủy - Furniture";
                    emailContent = "<div style=\"text-align: center; padding: 20px; font-family: Arial, sans-serif; color: #333;\">\n"
                            + "    <h2 style=\"color: #d9534f;\">Thông Báo Hủy Đơn Hàng</h2>\n"
                            + "    <p>Chúng tôi rất tiếc phải thông báo rằng đơn hàng của bạn đã bị hủy do nhân viên giao hàng không thể liên lạc được với bạn.</p>\n"
                            + "    <p>Bạn sẽ được hoàn 90% số tiền của đơn hàng. Để được hỗ trợ và hoàn trả lại số tiền, vui lòng liên hệ với bộ phận chăm sóc khách hàng của chúng tôi:</p>\n"
                            + "    <div style=\"margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; max-width: 500px; text-align: left;\">\n"
                            + "        <p><strong>Tên Sale:</strong>" + name
                            + "        <p><strong>Email:</strong>" + email
                            + "        <p><strong>Số Điện Thoại:</strong> " + phone
                            + "    </div>\n"
                            + "    <p>Chúng tôi xin lỗi vì sự bất tiện này và rất mong được phục vụ bạn trong các lần mua sắm tiếp theo. Cảm ơn bạn đã chọn sản phẩm của chúng tôi!</p>\n"
                            + "</div>";
                    break;
            }

            msg.setSubject(subject);
            msg.setContent(emailContent, "text/html; charset=utf-8");

            Transport.send(msg);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("An error occurred while sending the email.");
        }
    }

    public static void main(String[] args) {
      
       
    
    }
}
