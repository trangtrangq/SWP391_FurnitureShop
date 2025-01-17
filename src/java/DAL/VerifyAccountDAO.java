/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DAL.DBContext;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HELLO
 */
public class VerifyAccountDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(VerifyAccountDAO.class.getName());

    public void insertVerifyCustomer(String fullname, String gender, String phonenumber, String address, String email, String password) {
        String mysql = "INSERT INTO `furniture`.`verifyaccount` "
                + "(`fullname`, `gender`, `phonenumber`, `address`, `email`, `password`, `time`) "
                + "VALUES (?, ?, ?, ?, ?, ?, current_timestamp)";
        try (PreparedStatement statement = connect.prepareStatement(mysql)) {
            statement.setString(1, fullname);
            statement.setString(2, gender);
            statement.setString(3, phonenumber);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setString(6, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting verified customer", e);
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

   public void signUpUser(User user) {
       String hassPassword = userDAO.hashPassword(user.getPassword());
        String mysql = "INSERT INTO `furniture`.`verifyaccount`\n"
                + "(\n"
                + "`fullname`,\n"
                + "`gender`,\n"
                + "`phonenumber`,\n"
                + "`address`,\n"
                + "`email`,\n"
                + "`password`,\n"
                + "`role_id`,\n"
                + "`status`,\n"
                + "`time`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?,current_timestamp)";
        try(PreparedStatement statement = connect.prepareStatement(mysql)){
            statement.setString(1, user.getFullname());
            statement.setString(2, user.getGender());
            statement.setString(3, user.getPhonenumber());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getEmail());
              statement.setString(6, hassPassword);
            statement.setInt(7, user.getRole_id());
            statement.setString(8, user.getStatus());
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Error inserting verified customer", e);
        }
    }     
    UserDAO userDAO = new UserDAO();

}
