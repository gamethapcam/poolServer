/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.model;

/**
 * @author messenger
 */
public class ClientMetaData {
    public ClientMetaData() {
    }

    public ClientMetaData(String userName, double rate, String avatar, String phoneNumber, String fullName, String gender, String dob) {
        this.userName = userName;
        this.rate = rate;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    private String userName;
    private double rate;
    private String avatar;
    private String phoneNumber;
    private String fullName;
    private String gender;
    private String dob;
}
