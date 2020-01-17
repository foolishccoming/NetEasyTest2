package com.example.ts.neteasytest.LoginThings;

import android.support.v7.app.AppCompatActivity;

import java.security.PrivateKey;

/**
 * Created by ts on 20-1-14.
 */

public class UserPerson  /*BombUser*/  {
    private  String name ;
    private String password;
    private String mail;
    private String phoneNumber;

    public UserPerson() {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.phoneNumber = phoneNumber;

    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
