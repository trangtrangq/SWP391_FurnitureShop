/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAL.BrandDAO;
import DAL.CategoryDAO;
import DAL.CategoryOfPostDAO;
import DAL.ColorDAO;
import DAL.ProductDAO;
import DAL.RoomDAO;
import DAL.SaleOffDAO;
import DAL.UserRoleDAO;
import Helper.ConfigReaderHelper;
import Models.Brand;
import Models.Category;
import Models.CategoryOfPost;
import Models.Color;
import Models.Product;
import Models.Room;
import Models.SaleOff;
import Models.UserRole;
import jakarta.servlet.ServletContext;
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
 * @author HELLO
 */
public class SettingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ColorDAO colorDAO = new ColorDAO();
        ArrayList<Color> colorList = colorDAO.getColorList();
        request.setAttribute("colorList", colorList);

        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getCategoryList();
        request.setAttribute("categoryList", categoryList);

        SaleOffDAO saleOffDAO = new SaleOffDAO();
        ArrayList<SaleOff> saleOffList = saleOffDAO.getSaleOffList();
        request.setAttribute("saleOffList", saleOffList);

        CategoryOfPostDAO categoryOfPostDAO = new CategoryOfPostDAO();
        List<CategoryOfPost> categoryOfPost = categoryOfPostDAO.getCategoryOfPostList();
        request.setAttribute("categoryOfPostList", categoryOfPost);

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> roomList = roomDAO.getRoomList();
        request.setAttribute("roomList", roomList);

        BrandDAO brandDao = new BrandDAO();
        ArrayList<Brand> brandList = brandDao.getBrandList();
        request.setAttribute("brandList", brandList);

        UserRoleDAO userRoleDAO = new UserRoleDAO();
        ArrayList<UserRole> userRoleList = userRoleDAO.getUserRoleList();
        request.setAttribute("userRoleList", userRoleList);

        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> productList = productDAO.getProductList();
        request.setAttribute("productList", productList);

        ServletContext context = request.getServletContext();
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        
        int itemsHomeProductsList = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductInHomePage");
        request.setAttribute("itemsHomeProductsList", itemsHomeProductsList);
        
        int itemsPerProductListPage = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductListPage");
        request.setAttribute("itemsPerProductListPage", itemsPerProductListPage);
        
        int itemsPerFeedbackListPage = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerFeedbackListPage");
        request.setAttribute("itemsPerFeedbackListPage", itemsPerFeedbackListPage);
        
        int itemsPerCustomerListPage = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerCustomerListPage");
        request.setAttribute("itemsPerCustomerListPage", itemsPerCustomerListPage);
        
        int itemsPerPostList = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerPostList");
        request.setAttribute("itemsPerPostList", itemsPerPostList);
        
        int itemsPerSliderPage = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerSliderPage");
        request.setAttribute("itemsPerSliderPage", itemsPerSliderPage);
        
        int itemsPerOrderList = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerOrderList");
        request.setAttribute("itemsPerOrderList", itemsPerOrderList);
        
        int itemsPerShoppingCartsListPage = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerShoppingCartsListPage");
        request.setAttribute("itemsPerShoppingCartsListPage", itemsPerShoppingCartsListPage);
    }

    private boolean changeStatusUserRole(HttpServletRequest request) {
        int userRoleID = Integer.parseInt(request.getParameter("userRoleID"));
        String newStatus = request.getParameter("statusChange");
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        return userRoleDAO.changeStatus(userRoleID, newStatus);
    }

    private boolean changeStatusCategory(HttpServletRequest request) {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String newStatus = request.getParameter("statusChange");
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.changeStatus(categoryID, newStatus);
    }

    private boolean changeStatusBrand(HttpServletRequest request) {
        int brandID = Integer.parseInt(request.getParameter("brandID"));
        String newStatus = request.getParameter("statusChange");
        BrandDAO brandDAO = new BrandDAO();
        return brandDAO.changeStatus(brandID, newStatus);
    }

    private boolean changeStatusRoom(HttpServletRequest request) {
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        String newStatus = request.getParameter("statusChange");
        RoomDAO roomDAO = new RoomDAO();
        return roomDAO.changeStatus(roomID, newStatus);
    }

    private boolean changeStatusColor(HttpServletRequest request) {
        int colorID = Integer.parseInt(request.getParameter("colorID"));
        String newStatus = request.getParameter("statusChange");
        ColorDAO colorDAO = new ColorDAO();
        return colorDAO.changeStatus(colorID, newStatus);
    }

    private boolean changeStatusSaleOff(HttpServletRequest request) {
        int saleOffID = Integer.parseInt(request.getParameter("saleOffID"));
        String newStatus = request.getParameter("statusChange");
        SaleOffDAO saleOffDAO = new SaleOffDAO();
        return saleOffDAO.changeStatus(saleOffID, newStatus);
    }

    private boolean changeStatusCategoryOfPost(HttpServletRequest request) {
        int categoryOfPostID = Integer.parseInt(request.getParameter("categoryOfPostID"));
        String newStatus = request.getParameter("statusChange");
        CategoryOfPostDAO categoryOfPostDAO = new CategoryOfPostDAO();
        return categoryOfPostDAO.changeStatus(categoryOfPostID, newStatus);
    }

    private void changeItemsHomeProductsList(HttpServletRequest request, ServletContext context) {
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerHomeProductsList = Integer.parseInt(request.getParameter("itemsPerHomeProductsList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductInHomePage", itemsPerHomeProductsList);
    }
    
    private void changeItemsProductsList(HttpServletRequest request, ServletContext context) {
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerHomeProductsList = Integer.parseInt(request.getParameter("itemsPerProductsList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductListPage", itemsPerHomeProductsList);
    }
    
    private void changeItemsFeedbacksList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerFeedbackListPage = Integer.parseInt(request.getParameter("itemsPerFeedbacksList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerFeedbackListPage", itemsPerFeedbackListPage);
    }
    
    private void changeItemsCustomersList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerCustomerListPage = Integer.parseInt(request.getParameter("itemsPerCustomersList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerCustomerListPage", itemsPerCustomerListPage);
    }
    
    private void changeItemsPostsList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerPostList = Integer.parseInt(request.getParameter("itemsPerPostsList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerPostList", itemsPerPostList);
    }
    
    private void changeItemsSlidersList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerSliderPage = Integer.parseInt(request.getParameter("itemsPerSlidersList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerSliderPage", itemsPerSliderPage);
    }
    
    private void changeItemsOrdersList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerOrderList = Integer.parseInt(request.getParameter("itemsPerOrdersList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerOrderList", itemsPerOrderList);
    }
    
    private void changeItemsShoppingCartsList(HttpServletRequest request, ServletContext context){
        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
        String CONFIG_FILE_PATH = context.getRealPath("/");
        int itemsPerShoppingCartsListPage = Integer.parseInt(request.getParameter("itemsPerShoppingCartsList"));
        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerShoppingCartsListPage", itemsPerShoppingCartsListPage);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
//        ServletContext context = request.getServletContext();
//        ConfigReaderHelper configReaderHelper = new ConfigReaderHelper();
//        String CONFIG_FILE_PATH = context.getRealPath("/");
//        int itemsPerHomeProductsList = 2;
//        configReaderHelper.updateItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductInHomePage", itemsPerHomeProductsList);
//        int number = configReaderHelper.getValueOfItemsPerPage(CONFIG_FILE_PATH, "itemsPerProductInHomePage");
//        PrintWriter out = response.getWriter();
//        out.println(number);
        request.getRequestDispatcher("Views/Setting.jsp").forward(request, response);
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
        ServletContext context = request.getServletContext();
        String action = request.getParameter("action");
        if ("changeStatusUserRole".equals(action)) {
            boolean success = changeStatusUserRole(request);
        } else if ("changeStatusCategory".equals(action)) {
            boolean success = changeStatusCategory(request);
        } else if ("changeStatusBrand".equals(action)) {
            boolean success = changeStatusBrand(request);
        } else if ("changeStatusRoom".equals(action)) {
            boolean success = changeStatusRoom(request);
        } else if ("changeStatusColor".equals(action)) {
            boolean success = changeStatusColor(request);
        } else if ("changeStatusSaleOff".equals(action)) {
            boolean success = changeStatusSaleOff(request);
        } else if ("changeStatusCategoryOfPost".equals(action)) {
            boolean success = changeStatusCategoryOfPost(request);
        } else if ("changeItemsOfHomeProductsList".equals(action)) {
            changeItemsHomeProductsList(request, context);
        }else if("changeItemsOfProductsList".equals(action)){
            changeItemsProductsList(request, context);
        }else if("changeItemsOfFeedbacksList".equals(action)){
            changeItemsFeedbacksList(request, context);
        }else if("changeItemsOfCustomersList".equals(action)){
            changeItemsCustomersList(request, context);
        }else if("changeItemsOfPostsList".equals(action)){
            changeItemsPostsList(request, context);
        }else if("changeItemsOfSlidersList".equals(action)){
            changeItemsSlidersList(request, context);
        }else if("changeItemsOfOrdersList".equals(action)){
            changeItemsOrdersList(request, context);
        }else if("changeItemsOfShoppingCartsList".equals(action)){
            changeItemsShoppingCartsList(request, context);
        }
        processRequest(request, response);
        request.getRequestDispatcher("Views/Setting.jsp").forward(request, response);
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
