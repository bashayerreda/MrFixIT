package com.example.fixawy.Pojos;

public class ShopOwnerUser {
    private String phone;
    private String address;
    private String type;
    private String password;
    private String shopName;
    private  String shopType;

    public ShopOwnerUser() {
    }


    public ShopOwnerUser(String shopType) {
        this.shopType = shopType;
    }

    public ShopOwnerUser(String phone, String address, String type, String password, String shopName, String shopType) {
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.password = password;
        this.shopName = shopName;
        this.shopType = shopType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopType() {
        return shopType;
    }
}

