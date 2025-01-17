/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author HELLO
 */
import Models.Feedback;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(FeedbackDAO.class.getName());

    public ArrayList<Feedback> getFeedbackListByProductId(int productId) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE product_id = ? AND status = 'None'";

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                int productIdFromDb = rs.getInt("product_id"); // Rename to productIdFromDb to avoid conflict
                int voteScore = rs.getInt("votescore");
                String feedbackStr = rs.getString("feedback");
                String status = rs.getString("status");

                Feedback feedback = new Feedback(customerId, productIdFromDb, voteScore, feedbackStr);
                feedback.setId(id);
                feedback.setStatus(status);

                feedbackList.add(feedback);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving feedback list", ex);
        }

        return feedbackList;
    }

    // Phương thức getFeedbackList
    public ArrayList<Feedback> getFeedbackList() {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (
                PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                int productId = rs.getInt("product_id");
                int voteScore = rs.getInt("votescore");
                String feedbackStr = rs.getString("feedback");
                String status = rs.getString("status");
                Feedback feedback = new Feedback(customerId, productId, voteScore, feedbackStr);
                feedback.setId(id);
                feedback.setStatus(status);
                feedbackList.add(feedback);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving feedback list", ex);
        }

        return feedbackList;
    }

    // Phương thức insertFeedback
    public boolean insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (customer_id, product_id, votescore, feedback, status) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, feedback.getCustomer_id());
            pstmt.setInt(2, feedback.getProduct_id());
            pstmt.setInt(3, feedback.getVotescore());
            pstmt.setString(4, feedback.getFeedback());
            pstmt.setString(5, feedback.getStatus());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error inserting feedback", ex);
            return false;
        }
    }

    // Phương thức updateFeedback
    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE Feedback SET votescore = ?, feedback = ?, status = ? WHERE id = ?";

        try (
                PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, feedback.getVotescore());
            pstmt.setString(2, feedback.getFeedback());
            pstmt.setString(3, feedback.getStatus());
            pstmt.setInt(4, feedback.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating feedback", ex);
            return false;
        }
    }

    // Phương thức deleteFeedback
    public boolean deleteFeedback(int feedbackId) {
        String sql = "UPDATE Feedback SET status = 'Hide' WHERE id = ?";

        try (
                PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, feedbackId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting feedback", ex);
            return false;
        }
    }

    public boolean sendFeedback(int customer_id, int product_id, int votescore, String feedback, String status) {
        String sql = "INSERT INTO feedback (customer_id, product_id, votescore, feedback, status) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement stm = connect.prepareStatement(sql)) {
            stm.setInt(1, customer_id);
            stm.setInt(2, product_id);
            stm.setInt(3, votescore);
            stm.setString(4, feedback);
            stm.setString(5, status);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error sending feedback", e);
            return false;
        }
    }

    public boolean insertImageFb(int feedback_id, String image) {
        String sql = "INSERT INTO imagefeedback (feedback_id, image) VALUES (?, ?)";

        try (
                PreparedStatement stm = connect.prepareStatement(sql)) {
            stm.setInt(1, feedback_id);
            stm.setString(2, image);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting image feedback", e);
            return false;
        }
    }
    public String counts;

    public int countFeedbacks(int[] votescores, String[] statuses, String customerName, String productName, String description) {
        int count = 0;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT(*) AS total ")
                .append("FROM Feedback f ")
                .append("JOIN User u ON f.customer_id = u.id ")
                .append("JOIN Product p ON f.product_id = p.id ")
                .append("WHERE (");

        // Add placeholders for status array
        if (statuses.length > 0) {
            for (int i = 0; i < statuses.length; i++) {
                sql.append("f.status = ? ");
                if (i < statuses.length - 1) {
                    sql.append("OR ");
                }
            }
        } else {
            sql.append("f.status LIKE '%%' ");
        }

        sql.append(") AND u.fullname LIKE ? ")
                .append("AND p.name LIKE ? ")
                .append("AND f.feedback LIKE ? ");

        // Add placeholders for votescore array
        if (votescores.length > 0) {
            sql.append("AND (");
            for (int i = 0; i < votescores.length; i++) {
                sql.append("f.votescore = ? ");
                if (i < votescores.length - 1) {
                    sql.append("OR ");
                }
            }
            sql.append(") ");
        }

        try {
            PreparedStatement ps = connect.prepareStatement(sql.toString());
            int paramIndex = 1;

            // Set status parameters
            for (String status : statuses) {
                ps.setString(paramIndex++, status);
            }

            ps.setString(paramIndex++, "%" + customerName + "%");
            ps.setString(paramIndex++, "%" + productName + "%");
            ps.setString(paramIndex++, "%" + description + "%");

            // Set votescore parameters
            for (int votescore : votescores) {
                ps.setInt(paramIndex++, votescore);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

//    public static void main(String[] args) {
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
//        int list = feedbackDAO.countFeedbacks(0, "", "", "", "");
//        System.out.println(list);
//    }
    public String s;

    public List<Feedback> searchFeedbacks(int[] votescores, String[] statuses, String customerName, String productName, String description, int index, int pageSize, String sort) {
        List<Feedback> feedbackList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        UserDAO dAO = new UserDAO();
        ProductDAO aO = new ProductDAO();
        sql.append("SELECT f.id, f.customer_id, u.fullname AS customer_name, f.product_id, p.name AS product_name, f.votescore, f.feedback, f.status, p.description ")
                .append("FROM Feedback f ")
                .append("JOIN User u ON f.customer_id = u.id ")
                .append("JOIN Product p ON f.product_id = p.id ")
                .append("WHERE (");

        // Add placeholders for status array
        if (statuses.length > 0) {
            for (int i = 0; i < statuses.length; i++) {
                sql.append("f.status = ? ");
                if (i < statuses.length - 1) {
                    sql.append("OR ");
                }
            }
        } else {
            sql.append("f.status LIKE '%%' ");
        }

        sql.append(") AND u.fullname LIKE ? ")
                .append("AND p.name LIKE ? ")
                .append("AND f.feedback LIKE ? ");

        // Add placeholders for votescore array
        if (votescores.length > 0) {
            sql.append("AND (");
            for (int i = 0; i < votescores.length; i++) {
                sql.append("f.votescore = ? ");
                if (i < votescores.length - 1) {
                    sql.append("OR ");
                }
            }
            sql.append(") ");
        }

        switch (sort) {
            case "rate":
                sql.append("ORDER BY f.votescore ASC ");
                break;
            case "rate_desc":
                sql.append("ORDER BY f.votescore DESC ");
                break;
            default:
                sql.append("ORDER BY f.id ");
        }

        sql.append("LIMIT ?, ?");

        try {
            PreparedStatement ps = connect.prepareStatement(sql.toString());
            int paramIndex = 1;

            // Set status parameters
            for (String status : statuses) {
                ps.setString(paramIndex++, status);
            }

            ps.setString(paramIndex++, "%" + customerName + "%");
            ps.setString(paramIndex++, "%" + productName + "%");
            ps.setString(paramIndex++, "%" + description + "%");

            // Set votescore parameters
            for (int votescore : votescores) {
                ps.setInt(paramIndex++, votescore);
            }

            ps.setInt(paramIndex++, index);
            ps.setInt(paramIndex++, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(rs.getInt("id"));
                    feedback.setCustomer_id(rs.getInt("customer_id"));
                    feedback.setCustomer_Name(dAO.getUserNameByID(feedback.getCustomer_id()));
                    feedback.setProduct_id(rs.getInt("product_id"));
                    feedback.setProduct_Name(aO.getProductName(feedback.getProduct_id()));
                    feedback.setVotescore(rs.getInt("votescore"));
                    feedback.setFeedback(rs.getString("feedback"));
                    feedback.setStatus(rs.getString("status"));
                    feedbackList.add(feedback);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
    public static void main(String[] args) {
        FeedbackDAO aO = new FeedbackDAO();
        System.out.println(aO.searchFeedbacks(new int[0], new String[0], "", "", "", 6, 6, "rate").size());
    }

    public ArrayList<Feedback> SearchList(int vote, String status, String customer_name, String product_name, String Description, int index, int pagesize) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        //String sql11 = "select * from feedback where status like '%Active%' and votescore = 5 and feedback like '%jkahsahjks%'  limit 5 offset 5;";
        UserDAO ud = new UserDAO();
        ProductDAO pd = new ProductDAO();
        String sql = "select * from feedback \n"
                + "where ";
        if (status.length() != 0) {
            sql += " status like'%" + status + "%' and ";
        }
        if (vote == 0) {
            sql += " feedback like '%" + Description + "%'\n"
                    + "order by id asc\n"
                    + "limit " + pagesize + " offset " + (index - 1) * pagesize;
        } else {
            sql += "  votescore = " + vote + " and feedback like '%" + Description + "%'\n"
                    + "order by id asc\n"
                    + "limit " + pagesize + " offset " + (index - 1) * pagesize;
        }
        System.out.println(sql);

        try (
                PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                //System.out.println(ud.getUserNameByID(customerId)+" "+customer_name);
                //System.out.println(ud.getUserNameByID(customerId).contains(customer_name));
                String customer_name_str = ud.getUserNameByID(customerId);
                if (!customer_name_str.contains(customer_name)) {
                    continue;
                }
                int productId = rs.getInt("product_id");
                String product_name_str = pd.getProductName(productId);
                if (!product_name_str.contains(product_name)) {
                    continue;
                }
                int voteScore = rs.getInt("votescore");
                String feedbackStr = rs.getString("feedback");
                String status1 = rs.getString("status");
                Feedback feedback = new Feedback(customerId, productId, voteScore, feedbackStr);
                feedback.setId(id);
                feedback.setStatus(status1);
                feedback.setCustomer_Name(customer_name_str);
                feedback.setProduct_Name(product_name_str);
                feedbackList.add(feedback);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving feedback list", ex);
        }

        return feedbackList;
    }

//    public static void main(String[] args) {
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
//        String name = feedbackDAO.GetFeedbackByID(1);
//        System.out.println(name);
//    }
   

    public Feedback GetFeedbackByID(int id_re) {
        UserDAO ud = new UserDAO();
        ProductDAO pd = new ProductDAO();
        String sql = "select * from feedback where id= " + id_re;
        try (
                PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                //System.out.println(customerId+"sang");
                User u = ud.getUserById1(customerId);
                String customer_name_str = ud.getUserNameByID(customerId);
                int productId = rs.getInt("product_id");
                String product_name_str = pd.getProductName(productId);
                int voteScore = rs.getInt("votescore");
                String feedbackStr = rs.getString("feedback");
                String status1 = rs.getString("status");
                Feedback feedback = new Feedback(customerId, productId, voteScore, feedbackStr);
//                Feedback feedback = new Feedback(customerId, productId, voteScore, feedbackStr);
                feedback.setId(id);
                feedback.setStatus(status1);
                feedback.setCustomer_Name(customer_name_str);
                feedback.setProduct_Name(product_name_str);
                feedback.setUser(u);
                return feedback;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving feedback list", ex);
        }
        return null;
    }

    public boolean changeStatus(int id, String status) {
        String sql = "update feedback set status = '" + status + "' where id = " + id;
        try (
                PreparedStatement pstmt = connect.prepareStatement(sql)) {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating feedback", ex);
            return false;
        }
    }

    public String getImageFeedback(int id) throws SQLException {
        String sql = "select image from ImageFeedback where id = ?";
        try {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int getFeedbackWithMaxId() {
        int fbid = 0;
        String sql = "SELECT id FROM Feedback ORDER BY id DESC LIMIT 1";

        try (PreparedStatement stm = connect.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                fbid = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fbid;
    }

    public void insertHistory(int orderId, int feedbackId) {
        String sql = "INSERT INTO HistoryOrderFeedback (order_id, feedback_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, feedbackId);

            // Thi hành câu lệnh SQL
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }

    public int[] getHistory() {
        String sql = "SELECT order_id FROM HistoryOrderFeedback";
        List<Integer> orderIds = new ArrayList<>();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                orderIds.add(orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }

        // Chuyển danh sách orderIds thành mảng
        return orderIds.stream().mapToInt(i -> i).toArray();
    }

    //đếm số lượng feedback trong khoảng thời gian 
    public int getFeedbackCounts(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "select count(*) from feedback where CreateDate \n"
                + "between ? and ?";
        int count = 0;
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    //tính trung bình sao của tất cả feedback
    public double avgStarOfAllFeedbacks(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "select sum(f.votescore)/count(*) as avgstar\n"
                + "from feedback f\n"
                + "where f.createdate BETWEEN ? AND ?";
        double avg = 0;

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                avg = rs.getDouble("avgstar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avg;
    }

    //thống kê sao trung bình của feedback theo product category
    public Map<String, Double> getAvgStarByCategory(java.sql.Date startDate, java.sql.Date endDate) {
        Map<String, Double> starMap = new LinkedHashMap<>();

        String sql = "SELECT \n"
                + "    c.category,\n"
                + "    SUM(f.votescore)/count(c.category) AS avg_star    \n"
                + "FROM \n"
                + "    feedback f\n"
                + "JOIN\n"
                + "    product p ON f.product_id = p.id\n"
                + "JOIN \n"
                + "    category c ON p.category_id = c.id\n"
                + "WHERE \n"
                + "     f.createdate BETWEEN ? AND ? -- Thay bằng startdate và enddate\n"
                + "GROUP BY \n"
                + "    c.category";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double star = rs.getDouble("avg_star");
                starMap.put(category, star);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return starMap;
    }

//    public static void main(String[] args) {
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
//       String name = feedbackDAO.searchFeedbacks();
//        System.out.println(name);
//    }
}
