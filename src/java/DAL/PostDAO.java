/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Post;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HELLO
 */
public class PostDAO extends DBContext {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(PostDAO.class.getName());

    public ArrayList<Post> getFeaturedPostList() {
        String sql = "SELECT * FROM Post where status = 'featured'";
        ArrayList<Post> list = new ArrayList<>();

        try (PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving new post list", ex);
        }

        return list;
    }

    public ArrayList<Post> getPostList() {
            String sql = "SELECT * FROM Post where status != 'hide'";
        ArrayList<Post> list = new ArrayList<>();

        try (PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving post list", ex);
        }

        return list;
    }

     //get list of post by keyword searched
    public ArrayList<Post> getListPostbySearch(String keyword) {
        String sql = "SELECT p.*, c.category, u.fullname\n"
                + "FROM Post p\n"
                + "JOIN CategoryOfPost c ON p.category_id = c.id\n"
                + "JOIN User u ON p.mkt_id = u.id\n"
                + "where CONCAT(title, fullname, content, Subtitle) LIKE ? and p.status != 'hide'";
        ArrayList<Post> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
     //get list of all posts
    public List<Post> getListPost() {
        String sql = "SELECT * from Post where status != 'hide'";
        List<Post> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //get post detail by id
    public Post getPostbyID(String id) {
        String sql = "SELECT * from Post where id=?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //*************************** FOR MARKETING ****************************************
    //get posts list by filter
    public List<Post> getPostListMkt() {
        String sql = "SELECT * from Post";
        List<Post> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setTitle(rs.getString("title"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setThumbnail(rs.getString("thumbnail"));
                p.setContent(rs.getString("content"));
                p.setMkt_id(rs.getInt("mkt_id"));
                p.setUpdatedtime(rs.getString("updatedtime"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    ///update status ở trang posts list
    public void updateStatus(String status, String id) {
        String sql = "UPDATE `furniture`.`post`\n"
                + "SET\n"
                + "`status` = ?\n"
                + "WHERE `id` = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, status);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///update post detail
    public boolean updatePost(String id, String thumbnail, String title, String subtitle, String status, String content) {
        StringBuilder sql = new StringBuilder("UPDATE `furniture`.`post`\nSET\n");
        sql.append("`title` = ?,\n");
        sql.append("`subtitle` = ?,\n");
        sql.append("`content` = ?,\n");
        sql.append("`status` = ?\n");
        if (thumbnail != null) {
            sql.append(", `thumbnail` = ?\n");
        }
        sql.append("WHERE `id` = ?");

        try {
            PreparedStatement statement = connect.prepareStatement(sql.toString());
            int index = 1;
            statement.setString(index++, title);
            statement.setString(index++, subtitle);
            statement.setString(index++, content);
            statement.setString(index++, status);
            if (thumbnail != null) {
                statement.setString(index++, thumbnail);
            }
            statement.setString(index, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    ///create new post
    public boolean createPost(int mkt_id,int category_id , String thumbnail, String title, String subtitle, String status, String content) {
        String sql = "INSERT INTO `furniture`.`post`\n"
                + "(`category_id`,\n"
                + "`mkt_id`,\n"
                + "`title`,\n"
                + "`subtitle`,\n"
                + "`content`,\n"
                + "`thumbnail`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?);";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, category_id);
            statement.setInt(2, mkt_id);
            statement.setString(3, title);
            statement.setString(4, subtitle);
            statement.setString(5, content);
            statement.setString(6, thumbnail);
            statement.setString(7, status);
            
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList<Post> getPostListByCategoryId(String categoryOfPost_id) {
        String sql = "SELECT * FROM Post WHERE category_id = ? and status != 'hide'";
        ArrayList<Post> list = new ArrayList<>();
            try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, categoryOfPost_id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post();
                    p.setId(rs.getInt("id"));
                    p.setCategory_id(rs.getInt("category_id"));
                    p.setMkt_id(rs.getInt("mkt_id"));
                    p.setTitle(rs.getString("title"));
                    p.setSubtitle(rs.getString("subtitle"));
                    p.setThumbnail(rs.getString("thumbnail"));
                    p.setContent(rs.getString("content"));
                    p.setUpdatedtime(rs.getString("updatedtime"));
                    p.setStatus(rs.getString("status"));
                    list.add(p);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving post list by filter", ex);
        }

        return list;
    } 
   
   //đếm số lượng post trong khoảng thời gian 
    public int getPostCounts(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "select count(*) from post where CreateDate \n"
                + "between ? and ?";
        int count=0;
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count=rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
