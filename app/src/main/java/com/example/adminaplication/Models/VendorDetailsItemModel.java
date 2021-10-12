package com.example.adminaplication.Models;

public class VendorDetailsItemModel {
    String id,address,currentDate,email,fname,gender,isConfirm,lname,mobile,password,picture;


    public VendorDetailsItemModel() {
    }

    public VendorDetailsItemModel(String id, String address, String currentDate, String email, String fname, String gender, String isConfirm, String lname, String mobile, String password, String picture) {
        this.id = id;
        this.address = address;
        this.currentDate = currentDate;
        this.email = email;
        this.fname = fname;
        this.gender = gender;
        this.isConfirm = isConfirm;
        this.lname = lname;
        this.mobile = mobile;
        this.password = password;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
