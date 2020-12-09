package com.example.e_debt_book.model;

import java.io.Serializable;

public class Costumer implements Serializable {

    private String name ;
    private String lastname;
    private String phone;
    private String email;
    private int status = 0;


    public Costumer() {
    }

    public Costumer(String email) {
        this.email = email;
    }

    public Costumer(String name, String lastname, String email, int status) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.status = status;
    }

    public Costumer(String phone,int status) {
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

    public int getStatus() {
        return status;
    }

    public  void setStatus(int status) {
        this.status = status;
    }

    public String toString(){
        return "\n name : " + getName() +  "\n Last Name : " +getLastname() +"\n Phone : " + getPhone() + "\n Email : "+ getEmail()  +"\n Status : " + getStatus();
    }
}
