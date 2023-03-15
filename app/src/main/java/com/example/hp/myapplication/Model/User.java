package com.example.hp.myapplication.Model;

public class User {
    private String Email;
    private String Password;

    private String Phone;

    public User() {

    }

    public User(String email, String password) {
        Email = email;
        Password = password;
    }


    public String getName() {
        return Email;
    }

    public void setName(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
