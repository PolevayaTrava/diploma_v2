package com.example.diploma_v2.entity;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Orders {
    private Long orderId;
    private String date;
    private String status;
    private Customer customer;
    private Employees manager;
    private Employees picker;

    public Orders(Long orderId, String date, String status, Customer customer, Employees manager, Employees picker) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.manager = manager;
        this.picker = picker;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employees getManager() {
        return manager;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getPicker() {
        return picker;
    }

    public void setPicker(Employees picker) {
        this.picker = picker;
    }
}
