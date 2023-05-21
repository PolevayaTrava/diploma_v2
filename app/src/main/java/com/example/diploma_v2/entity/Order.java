package com.example.diploma_v2.entity;

import lombok.Getter;
import lombok.Setter;

public class Order {
    private Long id;
    private Orders orders;
    private Items items;
    private Integer count;
    private Integer countFact;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountFact() {
        return countFact;
    }

    public void setCountFact(Integer countFact) {
        this.countFact = countFact;
    }

    public Order(Long id, Orders orders, Items items, Integer count, Integer countFact) {
        this.id = id;
        this.orders = orders;
        this.items = items;
        this.count = count;
        this.countFact = countFact;
    }

    public String getItemId() {
        return items.getItemId();
    }
    public String getItemName() {
        return items.getItemName();
    }

    public String getRow() {
        return items.getRow();
    }

    public String getShelf() {
        return items.getShelf();
    }
}
