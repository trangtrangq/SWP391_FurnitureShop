/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Customer;

import Controller.Public.HomePage;
import DAL.BrandDAO;
import DAL.CartItemDAO;
import DAL.CategoryDAO;
import DAL.CategoryOfPostDAO;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.OrderDetailDAO;
import DAL.PageDAO;
import DAL.PostDAO;
import DAL.ProductDetailDAO;
import DAL.RoomDAO;
import DAL.SaleOffDAO;
import Models.Brand;
import Models.CartItem;
import Models.Category;
import Models.CategoryOfPost;
import Models.Color;
import Models.Feedback;
import Models.OrderDetail;
import Models.Page;
import Models.Post;
import Models.Room;
import Models.SaleOff;
import Models.User;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class AddToCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CartItemDAO cartItemDAO = new CartItemDAO();
        String action = request.getParameter("action");//mua ngay hoac chi add
        if (action.equals("buynow")) {
            try {
                cartItemDAO.updateCartItemStatus("unselected");
            } catch (SQLException ex) {
                Logger.getLogger(AddToCart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String productid = request.getParameter("productDetailId");
        int quantity = Integer.parseInt(request.getParameter("quantityproduct"));
        String price = request.getParameter("price");

        HttpSession Session = request.getSession(false);
        User customer = (User) Session.getAttribute("customer");
        CartItem cartItem = new CartItem();

        ProductDetailDAO productDetailDAO = new ProductDetailDAO();

        try {
            if (cartItemDAO.getCartItemByProductIdAndCustomerId(Integer.parseInt(productid), customer.getId()) != null) {

                cartItem = cartItemDAO.getCartItemByProductIdAndCustomerId(Integer.parseInt(productid), customer.getId());

                if ((cartItem.getQuantity() + quantity) > productDetailDAO.getProductDetail(Integer.parseInt(productid)).getQuantity()) {
                    //thoong baos looix
                    // chuyener huowng trang
                    return;
                }
                if (action.equals("buynow")) {
                    cartItemDAO.updateCartItemStatus(cartItem.getId(), "selected");
                }

                cartItemDAO.updateCartItemQuantity(cartItem.getId(), cartItem.getQuantity() + quantity, cartItem.getTotalcost() + Double.parseDouble(price) * quantity);
            } else {
                if (quantity > productDetailDAO.getProductDetail(Integer.parseInt(productid)).getQuantity()) {
                    //thoong baos looix chuyeen huowng
                    return;
                }
                cartItem.setCustomer_id(customer.getId());
                cartItem.setProduct_id(Integer.parseInt(productid));
                cartItem.setQuantity(quantity);
                cartItem.setTotalcost((long) (Double.parseDouble(price) * quantity));
                if (action.equals("buynow")) {
                    cartItem.setStatus("selected");
                }

                try {
                    cartItemDAO.addCartItem(cartItem);
                } catch (SQLException ex) {
                    Logger.getLogger(AddToCart.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddToCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (action.equals("buynow")) {
            response.sendRedirect("CartDetail");
        }
        JsonObject jsonResult = new JsonObject();
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("customer");

            try {
                jsonResult.addProperty("status", "success"); // Danh sách đơn hàng cho trang hiện tại
                jsonResult.addProperty("countcartitem", String.valueOf(cartItemDAO.countCartItemsByCustomerId(user.getId())));

            } catch (SQLException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResult.toString());

    }

    public static void main(String[] args) throws SQLException {
        CartItemDAO cartItemDAO = new CartItemDAO();
        CartItem cartItem = new CartItem();
        cartItem.setProduct_id(19);
        cartItem.setCustomer_id(1);
        cartItem.setQuantity(1);
        
        cartItem.setTotalcost((long) (Double.parseDouble("1590000.00") *1));

        cartItemDAO.addCartItem(cartItem);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}