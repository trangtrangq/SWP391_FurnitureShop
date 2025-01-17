/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author HELLO
 */
public class Order {
    private int id;
    private int customer_id;
    private int sale_id;
    private int address_id;

    private int paymentMethod_id;
    private double totalcost;
    private LocalDateTime orderDate;
    private String status;
    
    private int shipper_id;
    private String note;

    public Order() {
    }

    public Order(int id, int customer_id, int sale_id, int address_id, int paymentMethod_id, double totalcost, LocalDateTime orderDate, String status) {
        this.id = id;
        this.customer_id = customer_id;
        this.sale_id = sale_id;
        this.address_id = address_id;
        this.paymentMethod_id = paymentMethod_id;
        this.totalcost = totalcost;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Order(int customer_id, int sale_id, int address_id, double totalcost, LocalDateTime orderDate) {
        this.customer_id = customer_id;
        this.sale_id = sale_id;
        this.address_id = address_id;
        this.totalcost = totalcost;
        this.orderDate = orderDate;
        this.status = "Order";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getPaymentMethod_id() {
        return paymentMethod_id;
    }

    public void setPaymentMethod_id(int paymentMethod_id) {
        this.paymentMethod_id = paymentMethod_id;
    }

    public double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id = shipper_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
