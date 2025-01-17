/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Customer;

import DAL.AddressDAO;
import DAL.CartItemDAO;
import DAL.PaymentMethodDAO;
import Models.Address;
import Models.CartItemWithDetail;
import Models.PaymentMethod;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class AddNewAddress extends HttpServlet {
   
    


   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
         HttpSession session= request.getSession(false);
        User customer =(User) session.getAttribute("customer");
        CartItemDAO cartItemDAO = new CartItemDAO();
         List<CartItemWithDetail> cartItemWithDetails = new ArrayList<>();
        try {
            cartItemWithDetails = cartItemDAO.getCartItemsDetailByStatus(customer.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CartDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("listcartdetail", cartItemWithDetails);
        
        PaymentMethodDAO paymentMethodDAO= new PaymentMethodDAO();
        List<PaymentMethod> paymentMethods= new ArrayList<>();
         try {
             paymentMethods = paymentMethodDAO.getAllPaymentMethods();
         } catch (SQLException ex) {
             Logger.getLogger(AddNewAddress.class.getName()).log(Level.SEVERE, null, ex);
         }
        request.setAttribute("paymentmethodList", paymentMethods);
        
        double sumtotalprice = 0;
        try {
            sumtotalprice = cartItemDAO.getTotalCost(customer.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CartDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("sumtotalprice", sumtotalprice);
        AddressDAO addressDAO = new AddressDAO();
        
        
        String fullname = request.getParameter("fullname");
                
        String phonenumber = request.getParameter("phonenumber");
        String tinh = request.getParameter("tinh");
        String huyen= request.getParameter("huyen");
        String xa= request.getParameter("xa");
        String addressDetail = request.getParameter("addressdetail");
        String status= request.getParameter("status");
        Address address= new Address();
        address.setCustomer_id(customer.getId());
        address.setFullname(fullname);
        address.setPhonenumber(phonenumber);
        address.setAddress(xa+","+huyen+","+tinh);
        address.setAddressdetail(addressDetail);
        if(status!=null){
            addressDAO.updateStatusAddress("none", customer.getId());
            address.setStatus(status);
        }else{
            address.setStatus("none");
        }
        addressDAO.addAddress(address);
        List<Address> addresses= addressDAO.getAddressByCustomerId(customer.getId());
        request.setAttribute("addresslist", addresses);
         
        request.setAttribute("shouldOpenModal", true);
        request.getRequestDispatcher("Views/CartContact.jsp").forward(request, response);
    } 

    
    public static void main(String[] args) {
        AddressDAO addressDAO = new AddressDAO();
//        Address address= new Address(1, 5, "Nguyễn Tài Nam", "0846016236", "Hà Nội,Cầu Giấy,Dịch Vọng", "Số 39", "default");
//        addressDAO.addAddress(address);
        List<Address> addresses= addressDAO.getAddressByCustomerId(5);
        for (Address addresse : addresses) {
            System.out.println(addresse.getAddress());
        }
    }
    
    
}
