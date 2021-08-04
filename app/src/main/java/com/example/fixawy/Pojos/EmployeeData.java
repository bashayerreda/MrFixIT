package com.example.fixawy.Pojos;

public class EmployeeData {
    String address;
    String email;
    String jobTitle;
    String password;
    String phone;
    String type;
    String userName;

    public EmployeeData() {
    }

    public EmployeeData(String address, String email, String jobTitle, String password, String phone, String type, String userName) {
        this.address = address;
        this.email = email;
        this.jobTitle = jobTitle;
        this.password = password;
        this.phone = phone;
        this.type = type;
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
