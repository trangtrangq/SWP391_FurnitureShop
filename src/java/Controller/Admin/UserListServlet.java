/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAL.UserDAO;
import DAL.UserRoleDAO;
import Helper.ComparatorHelper;
import Helper.PaginationHelper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author admin
 */
public class UserListServlet extends HttpServlet {

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
        /* TODO output your page here. You may use following sample code. */
        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = userDAO.getUserList();
        request.setAttribute("userList", userList);

        request.getRequestDispatcher("Views/UserList.jsp").forward(request, response);
    }

//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        ArrayList<User> userList = userDAO.getUserList();
//        
//        UserRoleDAO userRoleDAO = new UserRoleDAO();
//        ArrayList<UserRole> userRoles = userRoleDAO.getUserRoleList();
//        for (User user : userList) {
//            for (UserRole userRole : userRoles) {
//                if(user.getRole_id() == userRole.getId()){
//                    System.out.println(user.toString());
//                }
//            }
//        }
//    }
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

        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Session đã bị xóa");
            return;
        }
        HttpSession session = request.getSession();
        String clearfilter = request.getParameter("clearfilter");
        if ((clearfilter != null && !clearfilter.isEmpty())) {
            session.removeAttribute("userList");
        }

        UserRoleDAO userRoleDAO = new UserRoleDAO();
        ArrayList<UserRole> userRole = userRoleDAO.getUserRoleList();
        request.setAttribute("userRole", userRole);

        ArrayList<User> userList = (ArrayList<User>) session.getAttribute("userList");

        if (userList == null) {
            UserDAO userDAO = new UserDAO();
            userList = userDAO.getUserList();
        }

        String sortby = request.getParameter("sortby");
        ComparatorHelper comparatorHelper = new ComparatorHelper();
        if (sortby != null && !sortby.isEmpty()) {
            userList = comparatorHelper.sortUserList(userList, sortby, userRole);
            session.setAttribute("userList", userList);
        }

        PaginationHelper paginationHelper = new PaginationHelper();
        ServletContext context = getServletContext();
        String itemsPerPage = "itemsPerUserListPage";
        String attribute = "userList";
        paginationHelper.Pagination(request, userList, context, itemsPerPage, attribute);
        request.getRequestDispatcher("Views/UserList.jsp").forward(request, response);
        
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
        String search = request.getParameter("search");
        String[] gender = request.getParameterValues("gender-filter");
        String[] role_id = request.getParameterValues("role_id-filter");
        String[] status = request.getParameterValues("status-filter");
        
        List<String> selectedGenderList = gender != null ? Arrays.asList(gender) : null;
        List<Integer> selectedRoleList = role_id != null ? Arrays.stream(role_id).map(Integer::parseInt).collect(Collectors.toList()) : null;
        List<String> selectedStatusList = status != null ? Arrays.asList(status) : null;
        
        request.setAttribute("selectedGenderList", selectedGenderList);
        request.setAttribute("selectedRoleList", selectedRoleList);
        request.setAttribute("selectedStatusList", selectedStatusList);

        UserRoleDAO userRoleDAO = new UserRoleDAO();
        ArrayList<UserRole> userRole = userRoleDAO.getUserRoleList();
        request.setAttribute("userRole", userRole);

        UserDAO userDAO = new UserDAO();
        ArrayList<User> userList = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            userList = userDAO.searchUserList(search);
        }else{
            userList = userDAO.filterUserList(gender, role_id, status);
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("userList", userList);
        request.setAttribute("userList", userList);

        PaginationHelper paginationHelper = new PaginationHelper();
        ServletContext context = getServletContext();
        String itemsPerPage = "itemsPerUserListPage";
        String attribute = "userList";
        paginationHelper.Pagination(request, userList, context, itemsPerPage, attribute);
        request.getRequestDispatcher("Views/UserList.jsp").forward(request, response);
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
