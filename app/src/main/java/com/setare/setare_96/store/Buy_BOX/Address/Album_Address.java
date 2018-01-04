package com.setare.setare_96.store.Buy_BOX.Address;

/**
 * Created by amirhossein on 12/8/17.
 */

public class Album_Address {


    private String name;
    private String phone_number;
    private String code_number;
    private String home_number;
    private String state;
    private String city;
    private String post_number;
    private String post_address;

    public Album_Address() {
    }

    public Album_Address(String name, String phone_number, String code_number, String home_number,
                         String state, String city, String post_number, String post_address) {
        this.name = name;
        this.phone_number = phone_number;
        this.code_number = code_number;
        this.home_number = home_number;
        this.state = state;
        this.city = city;
        this.post_number = post_number;
        this.post_address = post_address;
    }

    public String getNameRes() {
        return name;
    }
    public void setNameRes(String name) {
        this.name = name;
    }

    public String getPhone_Number() {
        return phone_number;
    }
    public void setPhone_Number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCode_number() {
        return code_number;
    }
    public void setCode_number(String code_number) {
        this.code_number = code_number;
    }

    public String getHome_Number() {
        return home_number;
    }
    public void setHome_Number(String home_number) {
        this.home_number = home_number;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getPost_number() {
        return post_number;
    }
    public void setPost_number(String post_number) {
        this.post_number = post_number;
    }

    public String getPost_address() {
        return post_address;
    }
    public void setPost_address(String post_address) {
        this.post_address = post_address;
    }

}

