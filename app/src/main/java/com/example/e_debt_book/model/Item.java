package com.example.e_debt_book.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String price;

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "name=" + name  + " , price=" + price ;
    }
}
