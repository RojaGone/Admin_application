package com.example.adminaplication.Models;

public class ShopProfileModel {
    String id,signup_city,signup_closetime,signup_opentime,signup_pincode,signup_shopaddress,signup_shoplogo,signup_shopname,signup_shopphoneNo;

    public ShopProfileModel(String id, String signup_city, String signup_closetime, String signup_opentime, String signup_pincode, String signup_shopaddress, String signup_shoplogo, String signup_shopname, String signup_shopphoneNo) {
        this.id = id;
        this.signup_city = signup_city;
        this.signup_closetime = signup_closetime;
        this.signup_opentime = signup_opentime;
        this.signup_pincode = signup_pincode;
        this.signup_shopaddress = signup_shopaddress;
        this.signup_shoplogo = signup_shoplogo;
        this.signup_shopname = signup_shopname;
        this.signup_shopphoneNo = signup_shopphoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignup_city() {
        return signup_city;
    }

    public void setSignup_city(String signup_city) {
        this.signup_city = signup_city;
    }

    public String getSignup_closetime() {
        return signup_closetime;
    }

    public void setSignup_closetime(String signup_closetime) {
        this.signup_closetime = signup_closetime;
    }

    public String getSignup_opentime() {
        return signup_opentime;
    }

    public void setSignup_opentime(String signup_opentime) {
        this.signup_opentime = signup_opentime;
    }

    public String getSignup_pincode() {
        return signup_pincode;
    }

    public void setSignup_pincode(String signup_pincode) {
        this.signup_pincode = signup_pincode;
    }

    public String getSignup_shopaddress() {
        return signup_shopaddress;
    }

    public void setSignup_shopaddress(String signup_shopaddress) {
        this.signup_shopaddress = signup_shopaddress;
    }

    public String getSignup_shoplogo() {
        return signup_shoplogo;
    }

    public void setSignup_shoplogo(String signup_shoplogo) {
        this.signup_shoplogo = signup_shoplogo;
    }

    public String getSignup_shopname() {
        return signup_shopname;
    }

    public void setSignup_shopname(String signup_shopname) {
        this.signup_shopname = signup_shopname;
    }

    public String getSignup_shopphoneNo() {
        return signup_shopphoneNo;
    }

    public void setSignup_shopphoneNo(String signup_shopphoneNo) {
        this.signup_shopphoneNo = signup_shopphoneNo;
    }
}
