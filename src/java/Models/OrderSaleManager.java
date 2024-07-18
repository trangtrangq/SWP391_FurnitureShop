/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class OrderSaleManager {

    private int id;
    private String customer;
    private double totalcost;
    private LocalDateTime orderDate;
    private String status;
    private String salename;

    public OrderSaleManager(int id, String customer, double totalcost, LocalDateTime orderDate, String status, String salename) {
        this.id = id;
        this.customer = customer;
        this.totalcost = totalcost;
        this.orderDate = orderDate;
        this.status = status;
        this.salename = salename;
    }

    public String getSalename() {
        return salename;
    }

    public void setSalename(String salename) {
        this.salename = salename;
    }

    public OrderSaleManager() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public OrderSaleManager(int id, String customer, double totalcost, LocalDateTime orderDate, String status) {
        this.id = id;
        this.customer = customer;
        this.totalcost = totalcost;
        this.orderDate = orderDate;
        this.status = status;
    }
}
