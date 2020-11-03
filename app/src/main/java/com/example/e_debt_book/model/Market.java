package com.example.e_debt_book.model;

public class Market {

    String name ;
    String password;
    int phone;
    String email;
    String iban;
    String adress;

    public Market() {
    }

    public Market(int phone) {
        this.phone = phone;
    }

    public Market(String name, String password, int phone, String email, String iban, String adress) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.iban = iban;
        this.adress = adress;
    }

    public Market(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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
}
