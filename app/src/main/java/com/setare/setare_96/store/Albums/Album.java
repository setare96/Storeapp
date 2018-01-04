package com.setare.setare_96.store.Albums;

public class Album {
    public String name;
    public String price;
    public String image;
    public int view_num;
    public int sell_number;
    public long upload_date;

    public Album() {
    }

    public Album(String name, String price, String image ,int view_num ,int sell_number ,long upload_date) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.view_num = view_num;
        this.sell_number = sell_number;
        this.upload_date = upload_date;
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

    public int getView_num() {
        return view_num;
    }
    public void setView_num(int view_num) {
        this.view_num = view_num;
    }

    public int getSell_number() {
        return sell_number;
    }
    public void setSell_number(int sell_number) {
        this.sell_number = sell_number;
    }

    public long getUpload_date() {
        return upload_date;
    }
    public void setUpload_date(long upload_date) {
        this.upload_date = upload_date;
    }
}
