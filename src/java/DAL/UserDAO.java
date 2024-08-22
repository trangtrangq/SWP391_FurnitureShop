/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.CustomerChanges;
import Models.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HELLO
 */
public class UserDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        ArrayList<User> userList = userDAO.getUserList();
//        for (User user : userList) {
//            System.out.println(user.getFullname());
//        }
//    }
    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }

        return userList;
    }

    public boolean changePass(String uid, String pass) {
//        pass = hashPassword(pass);
        try {
            String sql = "UPDATE `furniture`.`User` SET `password` = ? WHERE `id` = ?";
            try (PreparedStatement stm = connect.prepareStatement(sql)) {
                stm.setString(1, pass);
                stm.setString(2, uid);
                stm.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error changing password", e);
            return false;
        }
    }

    public boolean checkVerifyEmail(String email) {
        String mysql = "SELECT COUNT(*) FROM furniture.verifyaccount WHERE email = ?";
        try (PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking verified email", e);
        }
        return false;
    }

    public void insertVerifyCustomer(String fullname, String gender, String phonenumber, String address, String email, String password) {
        String mysql = """
                       INSERT INTO `furniture`.`verifyaccount`
                       (`fullname`,
                       `gender`,
                       `phonenumber`,
                       `address`,
                       `email`,
                       `password`,
                       `time`)
                       VALUES
                       (?,?,?,?,?,?,current_timestamp);""";
        try (
                PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, fullname);
            statement.setString(2, gender);
            statement.setString(3, phonenumber);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setString(6, password);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting verify customer", e);
        }
    }

    public User getUserbyID(String id) {
        String sql = "SELECT * from User where id=?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //user 
    public void signIn(User customer) {
        try (PreparedStatement statement = connect.prepareStatement(
                "INSERT INTO `furniture`.`user`"
                + "(`fullname`, `gender`, `phonenumber`, `address`, `email`, `password`, `role_id`, `status`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, customer.getFullname());
            statement.setString(2, customer.getGender());
            statement.setString(3, customer.getPhonenumber());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPassword());
            statement.setInt(7, customer.getRole_id());
            statement.setString(8, customer.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error signing in", e);
        }
    }

    public User login(String email, String password) {
        String query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setGender(rs.getString("gender"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setPhonenumber(rs.getString("phonenumber"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error logging in", e);
        }
        return null;
    }

    public void resetPassword(String email, String password) {
        String mysql = "UPDATE `furniture`.`user` SET `password` = ? WHERE `email` = ?";
        try (PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, password);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error resetting password", e);
        }
    }

    public Boolean checkAccount(String email) {
        String mysql = "SELECT `user`.`id` FROM `furniture`.`user` WHERE `user`.`email` = ?";
        try (PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking account", e);
        }
        return false;
    }

    public void insertCustomer(String email) {
        String selectSql = "SELECT fullname, gender, phonenumber, address, email, password, role_id, status "
                + "FROM verifyaccount "
                + "WHERE email = ?";
        String insertSql = "INSERT INTO `furniture`.`user` "
                + "(fullname, gender, phonenumber, address, email, password, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement selectStatement = connect.prepareStatement(selectSql); PreparedStatement insertStatement = connect.prepareStatement(insertSql)) {

            selectStatement.setString(1, email);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String fullname = rs.getString("fullname");
                String gender = rs.getString("gender");
                String phonenumber = rs.getString("phonenumber");
                String address = rs.getString("address");
                String password = rs.getString("password");
                int roleId = rs.getInt("role_id");
                String status = rs.getString("status");

                // Set các giá trị cho câu lệnh insert
                insertStatement.setString(1, fullname);
                insertStatement.setString(2, gender);
                insertStatement.setString(3, phonenumber);
                insertStatement.setString(4, address);
                insertStatement.setString(5, email);
                insertStatement.setString(6, password);
                insertStatement.setInt(7, roleId);
                insertStatement.setString(8, status);

                // Thực thi insert
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting customer", e);
        }
    }

    public User getUserByID(int id) {
        User user = new User();
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setGender(rs.getString("gender"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setPhonenumber(rs.getString("phonenumber"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String update(String fullname, String gender, String avatar, String phonenumber, String address, int uid) {
        String sql = "UPDATE User SET fullname = ?, gender = ?, avatar = ?, phonenumber = ?, address = ? WHERE id = ?";
        try (PreparedStatement stm = connect.prepareStatement(sql)) {
            stm.setString(1, fullname);
            stm.setString(2, gender);
            stm.setString(3, avatar);
            stm.setString(4, phonenumber);
            stm.setString(5, address);
            stm.setInt(6, uid);

            stm.executeUpdate();
            return "Update successful: " + sql;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public User getUserById(int uid) {
        try (PreparedStatement stm = connect.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            stm.setInt(1, uid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getMarketerList() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User u, UserRole ur where "
                + "u.role_id=ur.id and ur.rolename='Marketing'";

        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }

        return userList;
    }

    //get list of customer user
    public ArrayList<User> getCustomerList() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User u, UserRole ur where "
                + "u.role_id=ur.id and ur.rolename='Customer'";

        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }

        return userList;
    }

    //get list customer by status filter
    public ArrayList<User> getCustomerListbyStatus(String status) {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User u, UserRole ur where "
                + "u.role_id=ur.id and ur.rolename='Customer' and u.status=?";

        try (
                PreparedStatement statement = connect.prepareStatement(sql);) {
            statement.setString(1, status);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }

        return userList;
    }

    ///tao tai khoan cho khach hang (marketing)
    public boolean createAcc(String name, String gender, String phone, String add, String email, String pass) {
        String sql = "INSERT INTO `furniture`.`user`\n"
                + "(`fullname`,\n"
                + "`gender`,\n"
                + "`phonenumber`,\n"
                + "`address`,\n"
                + "`email`,\n"
                + "`password`,\n"
                + "`role_id`,\n"
                + "`status`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "1,\n"
                + "'Offline')";
        // Kiểm tra tên 0 ki tu
        if (name.isBlank()) {
            return false;
        }
        // Tên k chứa kt đặc biệt
        if (!name.matches("^[\\p{L}\\s]+$")) {
            return false;
        }

        // Kiểm tra địa chỉ 0 kí tự
        if (name.isBlank()) {
            return false;
        }

        // Kiểm tra sự hiện diện của ký tự '@'
        if (!email.contains("@")) {
            return false; // VP3
        }

        // Kiểm tra sự hiện diện của local part và domain part
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            return false; // IP4, IP5, IP6
        }

        // Kiểm tra xem domain có chứa ít nhất một dấu chấm không
        if (!parts[1].contains(".")) {
            return false; // IP10: Missing dot in domain
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu gạch ngang
        if (parts[1].startsWith("-") || parts[1].endsWith("-")) {
            return false; // IP11: Domain starts/ends with hyphen
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu chấm
        if (parts[1].startsWith(".") || parts[1].endsWith(".")) {
            return false; // IP10: Domain starts/ends with dot
        }

        // Kiểm tra định dạng số điện thoại (bắt đầu bằng 0 và gồm chính xác 10 ký tự)
        String phoneRegex = "^0\\d{9}$";
        if (!phone.matches(phoneRegex)) {
            return false;
        }

        if (checkAccount(email)) {
            return false;
        }
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, phone);
            statement.setString(4, add);
            statement.setString(5, email);
            statement.setString(6, pass);

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }
        return false;
    }

    //update account cua khach hang(mkt)
    public boolean updateCustomer(String id, String name, String gender, String phone, String add, String email) {
        // Kiểm tra tên 0 ki tu
        if (name.isBlank()) {
            return false;
        }
        // Tên k chứa kt đặc biệt
        if (!name.matches("^[\\p{L}\\s]+$")) {
            return false;
        }

        // Kiểm tra địa chỉ 0 kí tự
        if (name.isBlank()) {
            return false;
        }

        // Kiểm tra sự hiện diện của ký tự '@'
        if (!email.contains("@")) {
            return false; // VP3
        }

        // Kiểm tra sự hiện diện của local part và domain part
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            return false; // IP4, IP5, IP6
        }

        // Kiểm tra xem domain có chứa ít nhất một dấu chấm không
        if (!parts[1].contains(".")) {
            return false; // IP10: Missing dot in domain
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu gạch ngang
        if (parts[1].startsWith("-") || parts[1].endsWith("-")) {
            return false; // IP11: Domain starts/ends with hyphen
        }

        // Kiểm tra xem domain không bắt đầu hoặc kết thúc bằng dấu chấm
        if (parts[1].startsWith(".") || parts[1].endsWith(".")) {
            return false; // IP10: Domain starts/ends with dot
        }

        // Kiểm tra định dạng số điện thoại (bắt đầu bằng 0 và gồm chính xác 10 ký tự)
        String phoneRegex = "^0\\d{9}$";
        if (!phone.matches(phoneRegex)) {
            return false;
        }

        String sql = "UPDATE `furniture`.`user`\n"
                + "SET\n"
                + "`fullname` = ?,\n"
                + "`gender` = ?,\n"
                + "`phonenumber` = ?,\n"
                + "`address` = ?,\n"
                + "`email` = ?\n"
                + "WHERE `id` = ?";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, phone);
            statement.setString(4, add);
            statement.setString(5, email);
            statement.setString(6, id);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /// lay danh sach status
    public List<String> getListStatus() {
        String sql = "select distinct status from user";
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String s = "";
                s = rs.getString("status");
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //lay ra danh sach customerchanges by customerid
    public List<CustomerChanges> getListCustomerChangesbyId(String id) {
        String sql = "select * from customerchanges where customer_id=?";
        List<CustomerChanges> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CustomerChanges cc = new CustomerChanges();
                cc.setFullname(rs.getString("fullname"));
                cc.setEmail(rs.getString("email"));
                cc.setAddress(rs.getString("address"));
                cc.setPhone(rs.getString("phone"));
                cc.setGender(rs.getString("gender"));
                cc.setUpdatedby(rs.getInt("updatedby"));
                cc.setUpdateddate(rs.getString("updateddate"));
                list.add(cc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    //cap nhat vao bang customerchanges sau khi update
    public void addToCustomerChanges(CustomerChanges cc) {
        String sql = "INSERT INTO `furniture`.`customerchanges`\n"
                + "(`email`,\n"
                + "`fullname`,\n"
                + "`gender`,\n"
                + "`phone`,\n"
                + "`address`,\n"
                + "`updatedby`,\n"
                + "`customer_id`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?,\n"
                + "?);";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, cc.getEmail());
            statement.setString(2, cc.getFullname());
            statement.setString(3, cc.getGender());
            statement.setString(4, cc.getPhone());
            statement.setString(5, cc.getAddress());
            statement.setInt(6, cc.getUpdatedby());
            statement.setInt(7, cc.getCustomer_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserNameByID(int id) {
        String query = "SELECT fullname FROM user WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById1(int uid) {
        try (PreparedStatement stm = connect.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            stm.setInt(1, uid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean UpdateUser(int role_id, String status, int id) {
        if (role_id == 5 && status.equals("Block")) {
            System.out.println("Update not allowed for role_id 5");
            return false;
        }
        String sql = "UPDATE `furniture`.`user`\n"
                + "SET\n"
                + "`role_id` = ?, "
                + "`status` = ? "
                + "WHERE `id` = ? ";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setInt(1, role_id);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }

    public ArrayList<User> searchUserList(String search) {
        ArrayList<User> userList = new ArrayList<>();
        String mysql = "SELECT `user`.`id`,\n"
                + "    `user`.`fullname`,\n"
                + "    `user`.`gender`,\n"
                + "    `user`.`avatar`,\n"
                + "    `user`.`phonenumber`,\n"
                + "    `user`.`address`,\n"
                + "    `user`.`email`,\n"
                + "    `user`.`password`,\n"
                + "    `user`.`role_id`,\n"
                + "    `user`.`status`\n"
                + "FROM `furniture`.`user`\n"
                + "WHERE fullname LIKE ? OR email = ? OR phonenumber = ?";
        try (PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, "%" + search + "%");
            statement.setString(2, search);
            statement.setString(3, search);
            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setGender(rs.getString("gender"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setPhonenumber(rs.getString("phonenumber"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setStatus(rs.getString("status"));
                    userList.add(user);

                }

            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return userList;
    }

    private void appendPlaceholders(StringBuilder sql, int count) {
        for (int i = 0; i < count; i++) {
            sql.append("?");
            if (i < count - 1) {
                sql.append(", ");
            }
        }
    }

    public ArrayList<User> filterUserList(String[] gender, String[] roleID, String[] status) {
        ArrayList<User> userList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT DISTINCT
            `user`.`id`,
            `user`.`fullname`,
            `user`.`gender`,
            `user`.`avatar`,
            `user`.`phonenumber`,
            `user`.`address`,
            `user`.`email`,
            `user`.`password`,
            `user`.`role_id`,
            `user`.`status`
        FROM
            `furniture`.`user`
        
        WHERE 1=1
    """);

        List<Object> parameters = new ArrayList<>();

        if (gender != null && gender.length > 0) {
            sql.append(" AND `user`.`gender` IN (");
            appendPlaceholders(sql, gender.length);
            sql.append(")");
            parameters.addAll(Arrays.asList(gender));
        }

        if (roleID != null && roleID.length > 0) {
            sql.append(" AND `user`.`role_id` IN (");
            appendPlaceholders(sql, roleID.length);
            sql.append(")");
            parameters.addAll(Arrays.asList(roleID));
        }

        if (status != null && status.length > 0) {
            sql.append(" AND `user`.`status` IN (");
            appendPlaceholders(sql, status.length);
            sql.append(")");
            parameters.addAll(Arrays.asList(status));
        }

        try (PreparedStatement pstmt = connect.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullname(rs.getString("fullname"));
                    user.setGender(rs.getString("gender"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setPhonenumber(rs.getString("phonenumber"));
                    user.setAddress(rs.getString("address"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole_id(rs.getInt("role_id"));
                    user.setStatus(rs.getString("status"));
                    userList.add(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list.", ex);
        }

        return userList;
    }

    //đếm số lượng khách hàng trong khoảng thời gian 
    public int getCustomerCounts(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "select count(*) from user where CreateDate \n"
                + "between ? and ? and role_id=1";
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

    //đếm số lượng khách hàng theo ngày trong khoảng thời gian tuỳ chỉnh
    public List<Map<String, Object>> getCustomerCountsByDate(java.sql.Date startDate, java.sql.Date endDate) {
        List<Map<String, Object>> customerCounts = new ArrayList<>();
        String sql = "WITH RECURSIVE DateRange AS (\n"
                + "    SELECT ? AS Date -- Thay bằng startdate\n"
                + "    UNION ALL\n"
                + "    SELECT Date + INTERVAL 1 DAY\n"
                + "    FROM DateRange\n"
                + "    WHERE Date + INTERVAL 1 DAY <= ? -- Thay bằng enddate\n"
                + ")\n"
                + "SELECT d.Date, COUNT(c.createdate) AS customer_count\n"
                + "FROM DateRange d\n"
                + "LEFT JOIN user c ON DATE(c.createdate) = d.Date and c.role_id=1\n"
                + "GROUP BY d.Date\n"
                + "ORDER BY d.Date;";

        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("date", rs.getDate("Date"));
                row.put("customer_count", rs.getInt("customer_count"));
                customerCounts.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerCounts;
    }

    /////////////////////////////////////// SALE /////////////////////////////////////
    //lấy danh sách các sale
    public ArrayList<User> getSaleList() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user where role_id=2";

        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user list", ex);
        }

        return userList;
    }

    //lấy số lượng khách mới đăng kí
    public int newRegisteredCounts(java.sql.Date startDate, java.sql.Date endDate) {
        int count = 0;
        String sql = "select count(u.createdate) as newregister\n"
                + "from user u\n"
                + "where u.role_id='1' and u.createdate between ? AND ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("newregister");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    //lấy số lượng khách mới mua hang
    public int newBoughtCounts(java.sql.Date startDate, java.sql.Date endDate) {
        int count = 0;
        String sql = "select count( distinct o.customer_id) as newbought\n"
                + "from `furniture`.`order` o \n"
                + "where orderdate between ? AND ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("newbought");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<User> getListSale() {
        String sql = "SELECT * from User where role_id= 2 AND status= 'Online'";
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<User> getListSale(String apartofname) {
        String sql = "SELECT * from User where role_id= 2 AND status= 'Online' AND fullname LIKE ?";
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, "%" + apartofname + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public String hashPassword(String password) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Băm mật khẩu và chuyển đổi thành mảng byte
            byte[] hashBytes = digest.digest(password.getBytes());

            // Chuyển đổi mảng byte thành chuỗi mã hóa Hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Chuyển đổi mỗi byte thành chuỗi Hex và thêm vào StringBuilder
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Trả về chuỗi mã hóa Hex
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý lỗi khi thuật toán không tồn tại
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public String generateToken(User user) {
        String timestamp = Long.toString(new Date().getTime());
        String data = "" + user.getEmail() + user.getPassword() + timestamp;
        String token = hashPassword(data);
        String sql = "UPDATE user SET token = ? WHERE id = ?";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, token);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating token in database", e);
        }
        return token;
    }

    public User checkToken(String token) {
        User u = null; // Khởi tạo với null để kiểm tra nếu không có người dùng
        String sql = "SELECT * FROM user WHERE token = ?";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            // Thiết lập tham số cho câu lệnh SQL
            statement.setString(1, token);

            // Thực thi câu lệnh và lấy kết quả
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Nếu có kết quả, khởi tạo đối tượng User và thiết lập các thuộc tính
                    u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullname(rs.getString("fullname"));
                    u.setGender(rs.getString("gender"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setPhonenumber(rs.getString("phonenumber"));
                    u.setAddress(rs.getString("address"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRole_id(rs.getInt("role_id"));
                    u.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving user by token", ex);
        }
        return u;
    }

    public void logOut(User user) {
        String sql = "UPDATE user SET token = ? WHERE id = ?";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, null);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating token in database", e);
        }
    }

    public ArrayList<User> getShipperList() {
        String sql = "SELECT * FROM User WHERE role_id = 6";
        ArrayList<User> list = new ArrayList<>();
        try (
                PreparedStatement statement = connect.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullname(rs.getString("fullname"));
                u.setGender(rs.getString("gender"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhonenumber(rs.getString("phonenumber"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole_id(rs.getInt("role_id"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getidsale() {
        String sql = "SELECT u.id, u.fullname, COUNT(o.id) AS order_count\n"
                + "FROM user u\n"
                + "LEFT JOIN `order` o ON u.id = o.sale_id\n"
                + "WHERE u.role_id = (SELECT id FROM userrole WHERE rolename = 'Sale') AND u.status IN('Online','Offline') -- Assuming 'Sale' is the role for salespeople\n"
                + "GROUP BY u.id, u.fullname\n"
                + "ORDER BY order_count ASC, u.id ASC\n"
                + "LIMIT 1;";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User findSale(int order_id) {
        String sql = "SELECT sale_id FROM `order` WHERE id = ?";
        int sale_id = 0;
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, order_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    sale_id = rs.getInt("sale_id");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving sale_id from order.", ex);
        }

        UserDAO userDAO = new UserDAO();
        return userDAO.getUserByID(sale_id);
    }

    public static void main(String[] args) {
        UserDAO u = new UserDAO();
        int sale = u.getidsale();
        System.out.println(sale);

    }
}
