package com.example.e_debt_book.model;

public class Costumer {

    String name ;
    String lastname;
    String password;
    int phone;
    String email;

    public Costumer() {
    }

    public Costumer(String email) {
        this.email = email;
    }

    public Costumer(String name, String lastname, String password, int phone, String email) {
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public Costumer(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
}
