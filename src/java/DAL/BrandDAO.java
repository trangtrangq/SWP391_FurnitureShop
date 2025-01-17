/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Brand;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author HELLO
 */
public class BrandDAO extends DBContext {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BrandDAO.class.getName());

//    public static void main(String[] args) {
//        BrandDAO brandDao = new BrandDAO();
//        ArrayList<Brand> brands = brandDao.getBrandList();
//        for (Brand brand : brands) {
//            System.out.println(brand.toString());
//        }
//    }
    public ArrayList<Brand> getBrandList() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM brand WHERE status = 'Active'";

        try (PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String brandname = rs.getString("brandname");
                String status = rs.getString("status");
                Brand brand = new Brand(brandname);
                brand.setId(id);
                brand.setStatus(status);
                brandList.add(brand);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving brand list.", ex);
        }
        return brandList;
    }
    
    public ArrayList<Brand> getBrandListAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM brand";

        try (PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String brandname = rs.getString("brandname");
                String status = rs.getString("status");
                Brand brand = new Brand(brandname);
                brand.setId(id);
                brand.setStatus(status);
                brandList.add(brand);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error retrieving brand list.", ex);
        }
        return brandList;
    }

    public boolean insertBrand(Brand brand) {
        String sql = "INSERT INTO brand (brandname, status) VALUES (?, ?)";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, brand.getBrandname());
            pstmt.setString(2, brand.getStatus());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error inserting brand", ex);
            return false;
        }
    }

    public boolean updateBrand(Brand brand) {
        String sql = "UPDATE brand SET brandname = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, brand.getBrandname());
            pstmt.setInt(2, brand.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error updating brand", ex);
            return false;
        }
    }

    public boolean changeStatus(int brandId, String newStatus) {
        String sql = "UPDATE brand SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, brandId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error deleting brand", ex);
            return false;
        }
    }

}
