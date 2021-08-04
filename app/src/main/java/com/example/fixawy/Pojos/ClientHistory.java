package com.example.fixawy.Pojos;

public class ClientHistory {

   public String time,date,jobTitle,phone,typeOfOrder,userName;

    public ClientHistory(String time, String date, String jobTitle, String phone, String typeOfOrder, String userName) {
        this.time = time;
        this.date = date;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.typeOfOrder = typeOfOrder;
        this.userName = userName;
    }

    public ClientHistory(){}


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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
