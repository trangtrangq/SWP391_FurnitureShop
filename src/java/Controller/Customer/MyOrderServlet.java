/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Customer;

import Controller.WebSocket.OrderUpdateEndpoint;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.OrderDAO;
import DAL.OrderDetailDAO;
import DAL.ProductDAO;
import DAL.ProductDetailDAO;
import DAL.UserDAO;
import Helper.PaginationHelper;
import Models.Color;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.ProductDetail;
import Models.User;
import Util.Email;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class MyOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String listOrder(HttpServletRequest request, ArrayList<Order> orderList) {
        StringBuilder htmlResponse = new StringBuilder();

        int[] order_IDs = new int[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            order_IDs[i] = orderList.get(i).getId();
        }

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.MyOrderDetails(order_IDs);

        ProductDetailDAO prDetailDAO = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = prDetailDAO.getAllProductDetails();

        ProductDAO prDAO = new ProductDAO();
        ArrayList<Product> productList = prDAO.getProductList();

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int[] historyFeedbackOrder = feedbackDAO.getHistory();

        PaginationHelper paginationHelper = new PaginationHelper();
        ServletContext context = getServletContext();
        String itemsPerPage = "itemsPerOrderList";
        orderList = paginationHelper.PaginationList(request, orderList, context, itemsPerPage);

        if (orderList.isEmpty()) {
            htmlResponse.append("<div class=\"no-order\">\n")
                    .append("    <p>Bạn chưa có đơn hàng nào.</p>\n")
                    .append("</div>\n");
        } else {
            for (Order order : orderList) {
                htmlResponse.append("<div class=\"order-card\" style=\"margin-bottom: 30px\">\n")
                        .append("    <h3>OrderID: #").append(order.getId()).append("</h3>\n")
                        .append("    <div style=\"display: flex; justify-content: flex-end\">\n")
                        .append("        <h6><b>Order Time: <u>").append(order.getOrderDate()).append("</u></b></h6>\n")
                        .append("    </div>\n")
                        .append("    <div class=\"table-responsive\">\n")
                        .append("        <table class=\"table\">\n")
                        .append("            <thead>\n")
                        .append("                <tr>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">Product</th>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">ProductName</th>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">Color</th>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">Price</th>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">Quantity</th>\n")
                        .append("                    <th style=\"background-color: white; text-align: center\">Total</th>\n")
                        .append("                </tr>\n")
                        .append("            </thead>\n")
                        .append("            <tbody>\n");

                boolean firstProduct = true;
                int productCount = 0;
                for (User user : userList) {
                    if (order.getCustomer_id() == user.getId()) {
                        for (OrderDetail orderDetail : orderDetailList) {
                            if (orderDetail.getOrder_id() == order.getId()) {
                                for (ProductDetail productDetail : productDetailList) {
                                    if (productDetail.getId() == orderDetail.getProductdetail_id()) {
                                        for (Product product : productList) {
                                            if (product.getId() == productDetail.getProduct_id()) {
                                                htmlResponse.append("                <tr>\n");

                                                if (firstProduct) {
                                                    htmlResponse.append("                    <td style=\"text-align: center; background-color: white;\"><img src=\"image/product/")
                                                            .append(product.getImage()).append("\" alt=\"Product\" style=\"width:100px\"></td>\n")
                                                            .append("                    <td style=\"text-align: center; background-color: white;\">")
                                                            .append(product.getName()).append("</td>\n");

                                                    for (Color color : colorList) {
                                                        if (productDetail.getColor_id() == color.getId()) {
                                                            htmlResponse.append("                    <td style=\"text-align: center; background-color: white;\">")
                                                                    .append(color.getColorname()).append("</td>\n");
                                                        }
                                                    }

                                                    htmlResponse.append("                    <td style=\"text-align: center; background-color: white;\">")
                                                            .append(formatCurrency(orderDetail.getPrice())).append("</td>\n")
                                                            .append("                    <td style=\"text-align: center; background-color: white;\">")
                                                            .append(orderDetail.getQuantity()).append("</td>\n")
                                                            .append("                    <td style=\"text-align: center; background-color: white;\">")
                                                            .append(formatCurrency(order.getTotalcost())).append("</td>\n");

                                                    firstProduct = false;
                                                } else {
                                                    productCount++;
                                                }

                                                htmlResponse.append("                </tr>\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (productCount >= 1) {
                            htmlResponse.append("                <tr>\n")
                                    .append("                    <td colspan=\"6\" style=\"text-decoration: none; color: black;  background-color: white;\">\n")
                                    .append("                        <a href=\"MyOrderInformationServlet?id=").append(order.getId())
                                    .append("\" style=\"text-decoration: none; color: black;\">Số sản phẩm khác: ").append(productCount)
                                    .append("</a>\n")
                                    .append("                    </td>\n")
                                    .append("                </tr>\n");
                        }

                        htmlResponse.append("                <tr>\n")
                                .append("                    <td colspan=\"6\" style=\"text-align: center; background-color: white;\">\n")
                                .append("                        <a href=\"MyOrderInformationServlet?id=").append(order.getId())
                                .append("\" style=\"text-decoration: none; color: black;\">Xem chi tiết đơn hàng</a>\n")
                                .append("                    </td>\n")
                                .append("                </tr>\n")
                                .append("            </tbody>\n")
                                .append("        </table>\n")
                                .append("        <div style=\"display: flex;justify-content: flex-end;\">\n")
                                .append("            <div></div>\n")
                                .append("            <div><b>Total: </b>").append(formatCurrency(order.getTotalcost())).append("</div>\n")
                                .append("        </div>\n")
                                .append("        <div class=\"button-order\" style=\"display: flex; justify-content: flex-end; margin-top: 10px\">\n")
                                .append("            <c:choose>\n");

                        String orderStatus = order.getStatus();
                        switch (orderStatus) {
                            case "Order":
                                htmlResponse.append("                <button style=\"width: 80px; height: 30px\" class=\"btn btn-secondary\">Đang xử lí</button>\n");
                                break;
                            case "Canceled":
                                htmlResponse.append("                <button style=\"width: 80px; height: 30px\" class=\"btn btn-danger\">Đã hủy</button>\n");
                                break;
                            case "Delivering":
                                htmlResponse.append("                <button class=\"btn btn-light\" style=\"width: 80px; height: 30px; background-color: aquamarine\" >Đang giao</button>\n");
                                break;
                            case "Delivered":
                                htmlResponse.append("<form action=\"MyOrderServlet\" method=\"post\">\n")
                                        .append("    <input type=\"hidden\" name=\"order_id\" value=\"").append(order.getId()).append("\"/>\n")
                                        .append("    <input type=\"hidden\" name=\"action\" value=\"feedbackRequest\"/>\n")
                                        .append("    <input type=\"hidden\" name=\"email\" value=\"").append(user.getEmail()).append("\"/>\n")
                                        .append("    <button type=\"submit\" class=\"btn btn-danger\" style=\"margin-left: 10px; background-color: purple; color: white;\">Đã nhận được đơn hàng</button>\n")
                                        .append("</form>\n");
                                break;

                            case "Deliveryfailed":
                                htmlResponse.append("                <button class=\"btn btn-light\" style=\"height: 30px; color: white; background-color: brown\" >Đơn hàng đã giao thất bại</button>\n");
                                break;
                            case "Confirmed":
                                htmlResponse.append("                <button style=\"height: 30px\" class=\"btn btn-info\">Đơn hàng đã được xác nhận</button>\n");
                                break;

                            case "Done":
                                boolean hasFeedback = false;
                                for (int history : historyFeedbackOrder) {
                                    if (order.getId() == history) {
                                        hasFeedback = true;
                                        break;
                                    }
                                }

                                if (hasFeedback) {
                                    htmlResponse.append("                <button class=\"btn btn-success\" style=\"height: 30px;\">Hoàn thành đơn hàng</button>\n");
                                } else {
                                    htmlResponse.append("                <button class=\"btn btn-warning\" style=\"height: 30px;\">Hoàn thành đơn hàng</button>\n");
                                }
                                break;
                            case "Wait":
                                htmlResponse.append("                <a href=\"#\" class=\"btn btn-light\" style=\"height: 30px; background-color: pink\">Thanh toán ngay</a>\n");
                                break;
                            default:
                                htmlResponse.append("                <button style=\"width: 80px; height: 30px\" class=\"btn btn-warning\">")
                                        .append(order.getStatus()).append("</button>\n");
                                break;
                        }

                        htmlResponse.append("            </c:choose>\n")
                                .append("        </div>\n")
                                .append("    </div>\n")
                                .append("</div>\n");
                    }
                }
            }
        }

        return htmlResponse.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");

        OrderDAO orderDAO = new OrderDAO();
        ArrayList<Order> orderList = orderDAO.myOrder(customer.getId());
        session.setAttribute("orderList", orderList);

        String htmlResponse = listOrder(request, orderList);
        request.setAttribute("htmlResponse", htmlResponse);
        request.getRequestDispatcher("Views/MyOrder.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        Email sendEmail = new Email();
        ArrayList<Order> orderList = new ArrayList<>();
        HttpSession session = request.getSession();
        if ("pagination".equals(action)) {
            orderList = (ArrayList<Order>) session.getAttribute("orderList");

            String htmlResponse = listOrder(request, orderList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(htmlResponse);
        } else if ("feedbackRequest".equals(action)) {
            String id = request.getParameter("order_id");
            int order_id;
            try {
                order_id = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.updateOrderStatus(order_id, "Done");
            OrderUpdateEndpoint.sendUpdate("update");
            sendEmail.sendMessageEmail(email, action, order_id);
            User customer = (User) session.getAttribute("customer");

            orderList = orderDAO.myOrder(customer.getId());
            session.setAttribute("orderList", orderList);

            String htmlResponse = listOrder(request, orderList);
            request.setAttribute("htmlResponse", htmlResponse);
            request.getRequestDispatcher("Views/MyOrder.jsp").forward(request, response);
        }

    }

    public String formatCurrency(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(',');
        symbols.setMonetaryDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0", symbols);
        return decimalFormat.format(number) + "₫";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
