/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Common;

import Controller.WebSocket.OrderUpdateEndpoint;
import DAL.UserDAO;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Invalidating session
        HttpSession session = request.getSession(false); // không tạo session mới nếu không tồn tại
        User customer = (User)session.getAttribute("customer");
        UserDAO userDAO = new UserDAO();
        userDAO.logOut(customer);
        userDAO.UpdateUser(customer.getRole_id(), "Offline", customer.getId());
        if (session != null) {
            session.invalidate(); // Hủy session hiện tại
            OrderUpdateEndpoint.sendUpdate("aaa");
        }
        
        // Redirect to login page or any other page after logout
        response.sendRedirect("HomePage"); // Chuyển hướng đến trang đăng nhập sau khi đăng xuất
    }

}
