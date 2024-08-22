/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Marketing;

import DAL.UserDAO;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CustomersList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO udao = new UserDAO();
        //lay danh sach khach hang
        List<User> listCus = udao.getCustomerList();
        request.setAttribute("listCus", listCus);
        request.getRequestDispatcher("Views/CustomersList.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO udao = new UserDAO();
        //lay ra cac thong tin da nhap de tao tai khoan
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String add = request.getParameter("address");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        //validate
        String error = "";
        // Kiểm tra tên không được để trống
        if (fullname.isBlank()) {
            error = "Tên không để trống!";
        }
        
        if(!fullname.matches("^[\\p{L}\\s]+$")){
            error = "Tên không chứa kí tự đặc biệt!";
        }

        // Kiểm tra độ dài của địa chỉ (từ 20 đến 320 ký tự)
        if (add.isBlank()) {
            error = "Địa chỉ không được để trống!";
        }

        // Kiểm tra định dạng email

        // Kiểm tra sự hiện diện của ký tự '@'
        if (!email.contains("@")) {
            error = "Email không hợp lệ!";
        }

        // Kiểm tra sự hiện diện của local part và domain part
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            error = "Email không hợp lệ!";
        }

        // Kiểm tra xem domain có chứa ít nhất một dấu chấm không
        if (!parts[1].contains(".")) {
            error = "Email không hợp lệ!";
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu gạch ngang
        if (parts[1].startsWith("-") || parts[1].endsWith("-")) {
            error = "Email không hợp lệ!";
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu chấm
        if (parts[1].startsWith(".") || parts[1].endsWith(".")) {
            error = "Email không hợp lệ!";
        }
        
        // Kiểm tra định dạng số điện thoại (bắt đầu bằng 0 và gồm chính xác 10 ký tự)
        String phoneRegex = "^0\\d{9}$";
        if (!phone.matches(phoneRegex)) {
            error = "Số điện thoại không hợp lệ!";
        }
        
        if (udao.createAcc(fullname, gender, phone, add, email, pass)) {
            request.setAttribute("result", "success");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("result", "failure");
        }

        processRequest(request, response);
    }

}