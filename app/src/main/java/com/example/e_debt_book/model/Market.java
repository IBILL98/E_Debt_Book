package com.example.e_debt_book.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Market implements Serializable {

    String name ;

    String phone;
    String email;
    String iban;
    String adress;
    ArrayList<Customer> myCustomers;
    ArrayList<String> myDebts;
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Market() {
    }


    public Market(String name, String phone, String email, String iban, String adress,int status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.iban = iban;
        this.adress = adress;
        this.status = status;

    }

    public ArrayList<Customer> getMyCustomers() {
        return myCustomers;
    }

    public void setMyCustomers(ArrayList<Customer> myCustomers) {
        this.myCustomers = myCustomers;
    }

    public Market(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public void addCustomer (Customer customer) {
        myCustomers.add(customer);
    }
}
