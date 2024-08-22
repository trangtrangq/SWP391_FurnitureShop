/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Sale;

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class OrderDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int[] historyFeedbackOrder = feedbackDAO.getHistory();

        request.setAttribute("address", address);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("userList", userList);
        request.setAttribute("colorList", colorList);
        request.setAttribute("productDetailList", productDetailList);
        request.setAttribute("productList", productList);

        request.setAttribute("historyFeedbackOrder", historyFeedbackOrder);
    }

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

        processRequest(request, response);
        request.setAttribute("order", order);
        request.setAttribute("orderDetailList", orderDetailList);
        request.getRequestDispatcher("Views/OrderDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("order_id");
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String saleName = request.getParameter("saleName");
        String saleEmail = request.getParameter("saleEmail");
        String salePhone = request.getParameter("salePhone");
        // Xử lý hành động dựa trên action
        if (id != null && action != null) {
            int order_id;
            try {
                order_id = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }
            Email sendEmail = new Email();
            if (action.equals("saleCanceledOrder")) {
                // Xử lý hủy đơn hàng
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.updateOrderStatus(order_id, "Canceled");
                List<OrderDetail> orderDetails = new OrderDetailDAO().getOrderDetailsByOrderId(order_id);
                for (OrderDetail orderDetail : orderDetails) {
                    ProductDetail productDetail = new ProductDetailDAO().getProductDetail(orderDetail.getProductdetail_id());
                    productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                    new ProductDetailDAO().updateProductDetail(productDetail);
                    Product product = new ProductDAO().getProductByID(productDetail.getProduct_id());
                    product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
                    new ProductDAO().updateProduct(product);
                }
                OrderUpdateEndpoint.sendUpdate("update");
                sendEmail.sendSaleEmail(email, action, order_id, saleName, saleEmail, salePhone);

                Order order = orderDAO.getMyOrder(order_id);
                int[] order_IDs = new int[1];
                order_IDs[0] = order_id;

                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                ArrayList<OrderDetail> orderDetailList = orderDetailDAO.MyOrderDetails(order_IDs);

                processRequest(request, response);
                request.setAttribute("order", order);
                request.setAttribute("orderDetailList", orderDetailList);
                request.getRequestDispatcher("Views/OrderDetail.jsp").forward(request, response);
            } else if (action.equals("orderConfirmed")) {
                // Xử lý xác nhận đơn hàng
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.updateOrderStatus(order_id, "Confirmed");
                OrderUpdateEndpoint.sendUpdate("update");
                // Chuyển hướng người dùng đến trang xác nhận
                //response.sendRedirect("orderConfirmation.jsp");
                sendEmail.sendMessageEmail(email, action, order_id);
                Order order = orderDAO.getMyOrder(order_id);
                int[] order_IDs = new int[1];
                order_IDs[0] = order_id;
                orderDAO.updateShipper(order_id, orderDAO.distributeOrder(0));
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                ArrayList<OrderDetail> orderDetailList = orderDetailDAO.MyOrderDetails(order_IDs);

                processRequest(request, response);
                request.setAttribute("order", order);
                request.setAttribute("orderDetailList", orderDetailList);
                request.getRequestDispatcher("Views/OrderDetail.jsp").forward(request, response);
            } else {
                // Xử lý trường hợp action không hợp lệ
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } else {
            // Xử lý trường hợp thiếu thông tin
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
        }
    }

}
