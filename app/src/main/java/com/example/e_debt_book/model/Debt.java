package com.example.e_debt_book.model;

import android.widget.EditText;

import java.io.Serializable;
import java.util.List;


public class Debt  implements Serializable {
    private String debtID;
    private String customerPhone;
    private String marketPhone;
    private String amount;
    private String description;
    private String dateOfLoan;
    private String dueDate;
    List<Item> itemList;

    public Debt() { }

    public Debt(String customerPhone, String marketPhone, String amount, String description, String dateOfLoan, String dueDate, List<Item> itemList) {
        this.customerPhone = customerPhone;
        this.marketPhone = marketPhone;
        this.amount = amount;
        this.description = description;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.itemList = itemList;
    }

    public Debt(String toString, String toString1, String toString2, String toString3, String toString4, String toString5) {
    }

    public Debt(String description, String amount) {
        this.amount = amount;
        this.description = description;

    }

    public Debt(EditText descriptionInput, EditText loanAmountInput) {
        this.amount = amount;
        this.description = description;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getMarketPhone() {
        return marketPhone;
    }

    public void setMarketPhone(String marketPhone) {
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

    public List<Item> getItemList() {
        return itemList;
    }

    public void setDebtID(String id) { this.debtID = id; }

    public String getDebtID() {return debtID;}

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }


    @Override
    public String toString() {
        return "Debt{" +
                "debtID='" + debtID + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", marketPhone='" + marketPhone + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", dateOfLoan='" + dateOfLoan + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
