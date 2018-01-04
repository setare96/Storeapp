package com.setare.setare_96.store.Buy_BOX;

/**
 * Created by amirhossein on 12/6/17.
 */

public class Album_Buy_BOX {

    private String name;
    private String price;
    private String image;
    private String warranty;
    private String seller;
    private String number;
    private int buy_number;

    public Album_Buy_BOX() {
    }

    public Album_Buy_BOX(String name, String price, String image, String warranty, String seller, String number ,int buy_number) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.warranty = warranty;
        this.seller = seller;
        this.number = number;
        this.buy_number = buy_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImages() {
        return image;
    }

    public void setImages(String image) {
        this.image = image;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }

}
