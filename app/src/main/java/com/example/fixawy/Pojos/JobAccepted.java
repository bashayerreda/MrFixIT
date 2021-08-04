package com.example.fixawy.Pojos;

public class JobAccepted {
    String time;
    String date;
    String location;
    String phone;
    String userName;

    public JobAccepted() {
    }

    public JobAccepted(String time, String date, String location, String phone, String userName) {
        this.time = time;
        this.date = date;
        this.location = location;
        this.phone = phone;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
