package com.example.fixawy.Pojos;

public class Product {

    private String productName,productDesc,productPrice,productImage,phoneOfShopOwner,shopName;


    public Product(){}

    public Product(String productName, String productDesc, String productPrice, String productImage) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public Product(String productName, String productDesc, String productPrice, String productImage, String phoneOfShopOwner, String shopName) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.phoneOfShopOwner = phoneOfShopOwner;
        this.shopName = shopName;
    }

    public String getPhoneOfShopOwner() {
        return phoneOfShopOwner;
    }

    public void setPhoneOfShopOwner(String phoneOfShopOwner) {
        this.phoneOfShopOwner = phoneOfShopOwner;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
