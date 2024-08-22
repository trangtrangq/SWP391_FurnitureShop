/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Shipper;

import Controller.WebSocket.OrderUpdateEndpoint;
import DAL.AddressDAO;
import DAL.CategoryDAO;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.OrderDAO;
import DAL.OrderDetailDAO;
import DAL.ProductDAO;
import DAL.ProductDetailDAO;
import DAL.UserDAO;
import Models.Address;
import Models.Category;
import Models.Color;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.ProductDetail;
import Models.User;
import Util.Email;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HELLO
 */
public class ShippingOrderDetailServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        int order_id;
        try {
            order_id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getMyOrder(order_id);
        int[] order_IDs = new int[1];
        order_IDs[0] = order_id;

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.MyOrderDetails(order_IDs);

        ProductDetailDAO prDetailDAO = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = prDetailDAO.getAllProductDetails();
        ProductDAO prDAO = new ProductDAO();
        ArrayList<Product> productList = prDAO.getProductList();

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();

        AddressDAO addressDAO = new AddressDAO();
        List<Address> address = addressDAO.getAllAddresses();

        request.setAttribute("address", address);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("userList", userList);
        request.setAttribute("colorList", colorList);
        request.setAttribute("productDetailList", productDetailList);
        request.setAttribute("productList", productList);
        request.setAttribute("orderDetailList", orderDetailList);
        request.setAttribute("order", order);

        request.getRequestDispatcher("Views/ShippingOrderDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int order_id = Integer.parseInt(request.getParameter("order_id"));
        OrderDAO orderDAO = new OrderDAO();
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");
        Email sendEmail = new Email();
        if("accept".equals(action)){
            orderDAO.updateOrderStatus(order_id, "Delivering");
            String receiverEmail = request.getParameter("customerEmail");
            sendEmail.sendSaleEmail( receiverEmail, "orderShipping", order_id, customer.getFullname(), customer.getEmail(), customer.getPhonenumber());
            OrderUpdateEndpoint.sendUpdate("updateShipper");
        }else if("reject".equals(action)){
            int shipper_id = orderDAO.distributeOrder(customer.getId());
            orderDAO.updateShipper(order_id, shipper_id);
            OrderUpdateEndpoint.sendUpdate("updateShipper");
            response.sendRedirect("ShippingOrderServlet");
        }else if("deliveryFailed".equals(action)){
            orderDAO.updateOrderStatus(order_id, "Deliveryfailed");
            String receiverEmail = request.getParameter("customerEmail");
            String note = request.getParameter("note");
            String reason = request.getParameter("reason");
            orderDAO.updateOrderNote(order_id, note);
            User sale = new UserDAO().findSale(order_id);
            if("byCustomer".equals(reason)){
                sendEmail.sendSaleEmail(receiverEmail, "deliveryFailedByCustomer", order_id, sale.getFullname(), sale.getEmail(), sale.getPhonenumber());
            }else{
                sendEmail.sendSaleEmail(receiverEmail, "saleCanceledOrder", order_id, sale.getFullname(), sale.getEmail(), sale.getPhonenumber());
            }
        }else if("deliverySuccess".equals(action)){
            orderDAO.updateOrderStatus(order_id, "Delivered");
            String receiverEmail = request.getParameter("customerEmail");
            sendEmail.sendMessageEmail(receiverEmail, "deliveryConfirmation", order_id);
        }
        Order order = orderDAO.getMyOrder(order_id);
        int[] order_IDs = new int[1];
        order_IDs[0] = order_id;

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.MyOrderDetails(order_IDs);

        ProductDetailDAO prDetailDAO = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = prDetailDAO.getAllProductDetails();
        ProductDAO prDAO = new ProductDAO();
        ArrayList<Product> productList = prDAO.getProductList();

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();

        AddressDAO addressDAO = new AddressDAO();
        List<Address> address = addressDAO.getAllAddresses();

        request.setAttribute("address", address);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("userList", userList);
        request.setAttribute("colorList", colorList);
        request.setAttribute("productDetailList", productDetailList);
        request.setAttribute("productList", productList);
        request.setAttribute("orderDetailList", orderDetailList);
        request.setAttribute("order", order);

        request.getRequestDispatcher("Views/ShippingOrderDetail.jsp").forward(request, response);
    }

}
