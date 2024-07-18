/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Customer;

import DAL.OrderDAO;
import DAL.UserDAO;
import Models.Order;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class OrderStatus extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sessionid= request.getParameter("sessionid");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_ResponseCode =request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo =request.getParameter("vnp_TransactionNo");
        String vnp_BankCode=request.getParameter("vnp_BankCode");
        String vnp_PayDate =request.getParameter("vnp_PayDate");
        String vnp_TransactionStatus=request.getParameter("vnp_TransactionStatus");
        if("00".equals(vnp_TransactionStatus)){
            request.setAttribute("vnp_TransactionStatus", "Thành Công");
            Order order = new OrderDAO().getMyOrder(Integer.parseInt(vnp_Amount));
            order.setStatus("Order");
        }else{
            request.setAttribute("vnp_TransactionStatus", "Thất Bại");
            Order order = new OrderDAO().getMyOrder(Integer.parseInt(vnp_Amount));
            order.setStatus("Wait");
        }
        request.setAttribute("vnp_TxnRef", vnp_TxnRef);
        request.setAttribute("vnp_PayDate", vnp_PayDate);
        request.setAttribute("vnp_OrderInfo", vnp_OrderInfo);
        request.setAttribute("vnp_Amount", vnp_Amount);
       
        UserDAO userDAO = new UserDAO();
        User customer = userDAO.getUserByID(Integer.parseInt(sessionid));
        HttpSession session = request.getSession();
        session.setAttribute("customer", customer);
        request.getRequestDispatcher("Views/OrderStatusPayment.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
