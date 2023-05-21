package com.example.diploma_v2.entity;

public class Items {
    private Long itemId;
    private String itemName;
    private Integer count;
    private Integer row;
    private Integer shelf;

    public String getItemId() {
        return itemId.toString();
    }
    public String getItemName() {
        return itemName;
    }

    public String getRow() {
        return row.toString();
    }

    public String getShelf() {
        return shelf.toString();
    }
}
