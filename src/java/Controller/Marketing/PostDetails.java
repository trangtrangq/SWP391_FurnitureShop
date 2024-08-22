/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Marketing;

import DAL.CategoryOfPostDAO;
import DAL.PostDAO;
import DAL.UserDAO;
import Helper.FileUploadHelper;
import Models.CategoryOfPost;
import Models.Post;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author DELL
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class PostDetails extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "image/post";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryOfPostDAO categoryOfPostDAO = new CategoryOfPostDAO();
        PostDAO pdao = new PostDAO();
        UserDAO udao = new UserDAO();
        //list category
        List<CategoryOfPost> listCategory = categoryOfPostDAO.getListCategoryofPost();
        request.setAttribute("listCategory", listCategory);


        //post detail
        String id = request.getParameter("id");
        Post p = pdao.getPostbyID(id);
        String author = udao.getUserbyID(String.valueOf(p.getMkt_id())).getFullname();
        request.setAttribute("author", author);
        request.setAttribute("post", p);

        request.getRequestDispatcher("Views/PostDetails.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostDAO pdao = new PostDAO();
        String id = request.getParameter("id");
        //lay file anh
        FileUploadHelper help = new FileUploadHelper();
        String filename = null; // Gán giá trị mặc định là null

        Part part = request.getPart("thumbnail");
        if (part != null && part.getSize() > 0) { // Kiểm tra part có tồn tại và có kích thước > 0
            filename = help.uploadFileAndReturnFileName(request, response, "thumbnail", UPLOAD_DIRECTORY);
        }

        //lay cac thong tin khac
        String title = request.getParameter("title");
        String subtitle = request.getParameter("subtitle");
        String status = request.getParameter("status");
        String content = request.getParameter("content");

        //update post details         
        boolean isSuccess = pdao.updatePost(id, filename, title, subtitle, status, content);
        if (isSuccess) {
            request.setAttribute("result", "success");
        } else {
            request.setAttribute("result", "failure");
        }

        processRequest(request, response);
    }

}