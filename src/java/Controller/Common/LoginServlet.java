/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Common;

import Controller.WebSocket.OrderUpdateEndpoint;
import DAL.BrandDAO;
import DAL.CategoryDAO;
import DAL.CategoryOfPostDAO;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.OrderDetailDAO;
import DAL.PageDAO;
import DAL.PostDAO;
import DAL.ProductDAO;
import DAL.RoomDAO;
import DAL.SaleOffDAO;
import DAL.SliderDAO;
import DAL.UserDAO;
import Models.Brand;
import Models.Category;
import Models.CategoryOfPost;
import Models.Color;
import Models.Feedback;
import Models.OrderDetail;
import Models.Page;
import Models.Post;
import Models.Product;
import Models.Room;
import Models.SaleOff;
import Models.Slider;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SliderDAO sliderDAO = new SliderDAO();
        List<Slider> sliders = sliderDAO.getAllSlidersWith("show");
        request.setAttribute("listslider", sliders);

        BrandDAO brandDAO = new BrandDAO();
        ArrayList<Brand> brandList = brandDAO.getBrandList();
        request.setAttribute("brandList", brandList);

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> roomList = roomDAO.getRoomList();
        request.setAttribute("roomList", roomList);

        PageDAO pageDAO = new PageDAO();
        ArrayList<Page> pageList = pageDAO.getPageList();
        request.setAttribute("pageList", pageList);

        CategoryOfPostDAO categoryOfPostDAO = new CategoryOfPostDAO();
        List<CategoryOfPost> categoryOfPost = categoryOfPostDAO.getCategoryOfPostList();
        request.setAttribute("categoryOfPostList", categoryOfPost);

        PostDAO postDAO = new PostDAO();
        ArrayList<Post> postList = postDAO.getPostList();
        request.setAttribute("postList", postList);

        SaleOffDAO saleOffDAO = new SaleOffDAO();
        ArrayList<SaleOff> saleOffList = saleOffDAO.getSaleOffList();
        request.setAttribute("saleOffList", saleOffList);

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ArrayList<Feedback> feedbackList = feedbackDAO.getFeedbackList();
        request.setAttribute("feedbackList", feedbackList);

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.getOrderDetailsList();
        request.setAttribute("orderDetailList", orderDetailList);

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();
        request.setAttribute("categoryList", categoryList);

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();
        request.setAttribute("colorList", colorList);

        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> productList = productDAO.getProductList();
        request.setAttribute("productList", productList);

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();
        request.setAttribute("userList", userList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDAO userDAO = new UserDAO();
        String hassPass = userDAO.hashPassword(password);
        User customer = userDAO.login(email, hassPass);
        if (customer != null && !("Block".equals(customer.getStatus()))) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            userDAO.UpdateUser(customer.getRole_id(), "Online", customer.getId());
            String token = userDAO.generateToken(customer);
            Cookie tokenCookie = new Cookie("authToken", token);
            tokenCookie.setHttpOnly(true); // Ngăn JavaScript truy cập
            tokenCookie.setSecure(true); // Chỉ gửi qua HTTPS
            tokenCookie.setPath("/"); // Cookie có hiệu lực cho toàn bộ ứng dụng
            tokenCookie.setMaxAge(60 * 60 * 24 * 10); // Cookie hết hạn sau 10 ngày
            response.addCookie(tokenCookie);
            
            response.sendRedirect("HomePage");
            OrderUpdateEndpoint.sendUpdate("login");
        }else if(customer != null && "Block".equals(customer.getStatus())){
            processRequest(request, response);
            request.setAttribute("showlogin", "block");
            request.setAttribute("errorlogin", "Tài khoản đang bị khóa.");
            request.getRequestDispatcher("Views/HomePage.jsp").forward(request, response);
        }else {
            processRequest(request, response);
            request.setAttribute("showlogin", "block");
            request.setAttribute("errorlogin", "Tài khoản hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("Views/HomePage.jsp").forward(request, response);
        }

    }
}
