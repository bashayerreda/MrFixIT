package com.example.fixawy.Pojos;

public class MakeOrder {
    String time;
    String date;
   int paymentMethod;
    String typeOfOrder;
    String location;
    String phone;
    String requestedPhone;
    String details;
    String jobTitle;
    String userName;

    public MakeOrder() {
    }

    public MakeOrder(String time, String date, int paymentMethod, String typeOfOrder, String location, String phone, String requestedPhone, String details, String jobTitle, String userName) {
        this.time = time;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.typeOfOrder = typeOfOrder;
        this.location = location;
        this.phone = phone;
        this.requestedPhone = requestedPhone;
        this.details = details;
        this.jobTitle = jobTitle;
        this.userName = userName;
    }

    public MakeOrder(String time, String date, String location, String phone, String userName) {
        this.time = time;
        this.date = date;
        this.location = location;
        this.phone = phone;
        this.userName = userName;
    }

    public MakeOrder(String time, String date, String typeOfOrder, String jobTitle, String userName, String phone) {
        this.time = time;
        this.date = date;
        this.typeOfOrder = typeOfOrder;
        this.jobTitle = jobTitle;
        this.userName = userName;
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
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

    public String getRequestedPhone() {
        return requestedPhone;
    }

    public void setRequestedPhone(String requestedPhone) {
        this.requestedPhone = requestedPhone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}