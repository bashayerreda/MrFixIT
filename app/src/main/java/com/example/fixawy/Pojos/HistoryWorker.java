package com.example.fixawy.Pojos;

public class HistoryWorker {
    String name;
    String phone;
    String date;
    String time;
    String address;

    long rate;

    public HistoryWorker() {
    }

    public HistoryWorker(String name, String phone, String date, String time, String address) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.address = address;
    }

    public HistoryWorker(long rate) {
        this.rate = rate;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
