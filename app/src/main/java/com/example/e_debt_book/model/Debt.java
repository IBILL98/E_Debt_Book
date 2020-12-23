package com.example.e_debt_book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Debt  implements Serializable {
    private int customerPhone;
    private int marketPhone;
    private String amount;
    private String description;
    private String dateOfLoan;
    private String dueDate;
    public Debt() { }

    public Debt(int customerPhone, int marketPhone, String amount, String description, String dateOfLoan, String dueDate, List<Item> itemList) {
        this.customerPhone = customerPhone;
        this.marketPhone = marketPhone;
        this.amount = amount;
        this.description = description;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        //this.itemList = itemList;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(int customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getMarketPhone() {
        return marketPhone;
    }

    public void setMarketPhone(int marketPhone) {
        this.marketPhone = marketPhone;
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
