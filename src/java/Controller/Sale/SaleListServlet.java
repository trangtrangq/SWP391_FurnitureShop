/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Sale;

import DAL.UserDAO;
import Models.OrderSaleManager;
import Models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
 * @author ADMIN
 */
public class SaleListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String apartofname = request.getParameter("apartofname");
        String pageStr= request.getParameter("page");
        UserDAO userDAO = new UserDAO();
        List<User> listsale = new ArrayList<>();
        if (apartofname != null) {
            listsale = userDAO.getListSale(apartofname);
        } else {
            listsale = userDAO.getListSale();
        }
        int page = 1; // Trang mặc định là 1
        int recordsPerPage = 1; // Số bản ghi trên mỗi trang (có thể thay đổi tùy vào yêu cầu)
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Xử lý lỗi chuyển đổi page
            }
        }
        int totalRecords = listsale.size(); // Tổng số bản ghi
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage); // Tổng số trang

        // Tính chỉ mục bắt đầu và kết thúc của danh sách đơn hàng cho trang hiện tại
        int startIndex = (page - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);
        JsonArray jsonArray = new JsonArray();
        for (User sale : listsale.subList(startIndex, endIndex)) {
            JsonObject jsonOrder = new JsonObject();
            jsonOrder.addProperty("id", sale.getId());
            jsonOrder.addProperty("fullname", sale.getFullname());
            jsonOrder.addProperty("gender", sale.getGender());
            jsonOrder.addProperty("phonenumber", sale.getPhonenumber());
            jsonOrder.addProperty("email", sale.getEmail());
            jsonOrder.addProperty("status", sale.getStatus());
            jsonArray.add(jsonOrder);
        }
        JsonObject jsonResult = new JsonObject();
        jsonResult.add("listsale", jsonArray); // Danh sách đơn hàng cho trang hiện tại
        jsonResult.addProperty("totalPage", totalPages);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResult.toString());
    }

    
}
