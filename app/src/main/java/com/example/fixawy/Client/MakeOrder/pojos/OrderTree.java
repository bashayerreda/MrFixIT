package com.example.fixawy.Client.MakeOrder.pojos;

public class OrderTree {
    private String typeOfOrder;
    private String details;
    private String location;
    private String phone;
    private String date;
    private String time;
    private String paymentMethod;
    private String requestedPhone;
    private String jobTitle;
    private String userName;

    public OrderTree(String time, String date, String location, String phone, String userName) {
        this.time = time;
        this.date = date;
        this.location = location;
        this.phone = phone;
        this.userName = userName;
    }

    public OrderTree(String time, String date, String typeOfOrder, String jobTitle, String userName, String phone) {
        this.time = time;
        this.date = date;
        this.typeOfOrder = typeOfOrder;
        this.jobTitle = jobTitle;
        this.userName = userName;
        this.phone = phone;
    }

    private String tokenid;

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getRequestedPhone() {
        return requestedPhone;
    }

    public void setRequestedPhone(String requestedPhone) {
        this.requestedPhone = requestedPhone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderTree(String location, String phone, String date, String time) {
        this.location = location;
        this.phone = phone;
        this.date = date;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderTree() {
    }

    public OrderTree(String typeOfOrder, String details) {
        this.typeOfOrder = typeOfOrder;
        this.details = details;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
