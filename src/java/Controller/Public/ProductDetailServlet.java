/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Public;

import Controller.Customer.CartDetail;
import DAL.AttachedImageDAO;
import DAL.BrandDAO;
import DAL.CartItemDAO;
import DAL.CategoryDAO;
import DAL.CategoryOfPostDAO;
import DAL.ColorDAO;
import DAL.FeedbackDAO;
import DAL.ImageFeedbackDAO;
import DAL.OrderDetailDAO;
import DAL.PageDAO;
import DAL.PostDAO;
import DAL.ProductDAO;
import DAL.ProductDetailDAO;
import DAL.RoomDAO;
import DAL.SaleOffDAO;
import DAL.SliderDAO;
import DAL.UserDAO;
import DAL.UserRoleDAO;
import Helper.PaginationHelper;
import Models.AttachedImage;
import Models.Brand;
import Models.CartItemWithDetail;
import Models.Category;
import Models.CategoryOfPost;
import Models.Color;
import Models.OrderDetail;
import Models.Page;
import Models.Post;
import Models.Product;
import Models.ProductDetail;
import Models.Room;
import Models.SaleOff;
import Models.Slider;
import Models.Feedback;
import Models.ImageFeedback;
import Models.User;
import Models.UserRole;
import jakarta.servlet.ServletContext;
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
 * @author HELLO
 */
public class ProductDetailServlet extends HttpServlet {

    private ArrayList<Product> ProductDisplay(HttpServletRequest request, ArrayList<Product> productList) {
        SaleOffDAO saleOffDAO = new SaleOffDAO();
        ArrayList<SaleOff> saleOffList = saleOffDAO.getSaleOffList();

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ArrayList<Feedback> feedbackList = feedbackDAO.getFeedbackList();

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.getOrderDetailsList();

        ProductDetailDAO pddao = new ProductDetailDAO();
        ArrayList<ProductDetail> productDetailList = pddao.getAllProductDetails();

        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();

        PaginationHelper paginationHelper = new PaginationHelper();
        ServletContext context = getServletContext();
        String itemsPerPage = "itemsPerProductInHomePage";
        productList = paginationHelper.PaginationList(request, productList, context, itemsPerPage);

        for (Product product : productList) {
            for (SaleOff saleOff : saleOffList) {
                if (saleOff.getProduct_id() == product.getId() && saleOff.getSaleoffvalue() != 0) {
                    product.setSaleOff(saleOff.getSaleoffvalue());
                    product.setSalePrice(product.getPrice() - (product.getPrice() * product.getSaleOff() / 100));
                    break;
                }
            }

            int reviewCount = 0;
            for (Feedback feedback : feedbackList) {
                if (feedback.getProduct_id() == product.getId()) {
                    reviewCount++;
                }
            }
            product.setNumberFeedback(reviewCount);

            int quantitySold = 0;
            for (OrderDetail orderDetail : orderDetailList) {
                for (ProductDetail productDetail : productDetailList) {
                    if (orderDetail.getProductdetail_id() == productDetail.getId() && productDetail.getProduct_id() == product.getId()) {
                        quantitySold += orderDetail.getQuantity();
                    }
                }
            }
            product.setQuantitySold(quantitySold);
            ArrayList<Color> newColorList = new ArrayList<>();
            for (ProductDetail productDetail : productDetailList) {
                if (product.getId() == productDetail.getProduct_id()) {
                    for (Color color : colorList) {
                        if (productDetail.getColor_id() == color.getId()) {
                            newColorList.add(color);
                        }
                    }
                }
            }
            product.setColorList(newColorList);
        }
        return productList;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        List<Slider> sliders = sliderDAO.getAllSlidersWith("show");
        request.setAttribute("listslider", sliders);

        BrandDAO brandDao = new BrandDAO();
        ArrayList<Brand> brandList = brandDao.getBrandList();
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

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();
        request.setAttribute("userList", userList);

        ImageFeedbackDAO ifdao = new ImageFeedbackDAO();
        ArrayList<ImageFeedback> imageFeedbackList = ifdao.getAllImageFeedbackList();
        request.setAttribute("imageFeedbackList", imageFeedbackList);

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("customer");

        request.setAttribute("customer", user);

        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> productList = productDAO.getProductList();
        productList = ProductDisplay(request, productList);

        request.setAttribute("productList", productList);

        int productId = tryParseInt(request.getParameter("productId"), 0);
        Product product = productDAO.getProductByID(productId);
        request.setAttribute("product", product);

        ProductDetailDAO pddao = new ProductDetailDAO();

        ArrayList<ProductDetail> productDetailList = pddao.getAllProductDetails();
        request.setAttribute("productDetailList", productDetailList);

        ArrayList<ProductDetail> productDetailofProduct = pddao.getProductDetailsByProductId(productId);
        request.setAttribute("productDetailofProduct", productDetailofProduct);

        int[] pdetailIdArr = new int[productDetailofProduct.size()];
        for (int i = 0; i < pdetailIdArr.length; i++) {
            pdetailIdArr[i] = productDetailofProduct.get(i).getId();
        }

        AttachedImageDAO attachedImageDAO = new AttachedImageDAO();
        ArrayList<AttachedImage> attachedImageList = attachedImageDAO.getAttachedImagesByProductDetailId(pdetailIdArr);
        request.setAttribute("attachedImageList", attachedImageList);

        ArrayList<Feedback> feedbacksOfProduct = feedbackDAO.getFeedbackListByProductId(productId);
        request.setAttribute("feedbacksOfProduct", feedbacksOfProduct);
        
        if (session != null) {
            if (user != null) {

                CartItemDAO cartItemDAO = new CartItemDAO();
                try {
                    request.setAttribute("countcart", cartItemDAO.countCartItemsByCustomerId(user.getId()));
                } catch (SQLException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<CartItemWithDetail> cartItemWithDetails = new ArrayList<>();
                try {
                    cartItemWithDetails = cartItemDAO.getCartItemsDetail(user.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.setAttribute("listcartdetail", cartItemWithDetails);
                double sumtotalprice = 0;
                try {
                    sumtotalprice = cartItemDAO.getTotalCostNoStatus(user.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(CartDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("sumtotalprice", sumtotalprice);
            }
        }   
        

        request.getRequestDispatcher("Views/ProductDetail.jsp").forward(request, response);
    }

    private int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

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

        processRequest(request, response);
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
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        String action = request.getParameter("action");
        if (action.equals("deleteFeedback")) {
            int deleteFeedbackId = tryParseInt(request.getParameter("deleteFeedbackId"), 0);
            feedbackDAO.deleteFeedback(deleteFeedbackId);
        }
        processRequest(request, response);
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
