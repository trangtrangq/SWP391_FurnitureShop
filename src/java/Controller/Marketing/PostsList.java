/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Marketing;

import DAL.CategoryOfPostDAO;
import DAL.PostDAO;
import DAL.UserDAO;
import Models.CategoryOfPost;
import Models.Post;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author DELL
 */
public class PostsList extends HttpServlet {

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
        CategoryOfPostDAO cdao = new CategoryOfPostDAO();
        PostDAO pdao = new PostDAO();
        UserDAO udao = new UserDAO();

        //lay ra list catecory, list user la mkt(de so sanh voi id cua post)
        List<CategoryOfPost> listCategory = cdao.getListCategoryofPost();
        request.setAttribute("listCategory", listCategory);
        List<User> listMkt = udao.getMarketerList();
        request.setAttribute("listMkt", listMkt);

        //get list post 
        List<Post> listPost = pdao.getPostListMkt();
        request.setAttribute("listPost", listPost);
        request.getRequestDispatcher("Views/PostsList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newstatus = request.getParameter("newstatus");
        String id = request.getParameter("id");
        PostDAO pdao = new PostDAO();
        pdao.updateStatus(newstatus, id);
        processRequest(request, response);
    }
}
