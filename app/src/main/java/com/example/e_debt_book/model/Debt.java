package com.example.e_debt_book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Debt  implements Serializable {
    private Customer customer;
    private Market market;
    private String amount;
    private String description;
    private String dateOfLoan;
    private String dueDate;

    public Debt(Customer customer, Market market, String amount, String description, String dateOfLoan, String dueDate, List<Item> itemList) {
        this.customer = customer;
        this.market = market;
        this.amount = amount;
        this.description = description;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.itemList = itemList;
    }

    private List<Item> itemList = new ArrayList<Item>();

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(String dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
