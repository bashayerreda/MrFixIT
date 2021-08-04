package com.example.fixawy.Pojos;

public class User {

    private String userName;
    private String email;
    private String phone;
    private String address;
    private String type;
    private String password;
    private String jobTitle;
    private String image;
    private int numOfJob;
    private int like;
    private int disLike;
    private int rating;

    private String tokenId;

    public User() {
    }

    public User(String image) {
        this.image = image;
    }

    public User(String userName, String phone, String address, String image) {
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public User(String userName, String email, String phoneNum, String address, String type, String password, String jobTitle, int numOfJob, int like, int disLike, int rating){}

//    public User(String userName, String email, String phone, String address, String type, String password) {
//        this.userName = userName;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.type = type;
//        this.password=password;
//    }

    public User(String userName, String email, String phone, String address, String type, String password,String tokenId) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.password=password;
        this.tokenId = tokenId;
    }

    public User(String userName, String email, String phone, String address, String type, String password, String jobTitle,String tokenId,String image) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.password = password;
        this.jobTitle = jobTitle;
        this.tokenId = tokenId;
        this.image = image;

    }

//    public User(String userName, String email, String phone, String address, String type, String password, String jobTitle) {
//        this.userName = userName;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.type = type;
//        this.password = password;
//        this.jobTitle = jobTitle;
//    }

//    public User(String userName, String phone, String address) {
//        this.userName = userName;
//        this.phone = phone;
//        this.address = address;
//    }



    public User(String userName, String email, String phone, String address, String type, String password, String jobTitle, String image, int numOfJob, int like, int disLike, int rating,String tokenId) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.password = password;
        this.jobTitle = jobTitle;
        this.image = image;
        this.numOfJob = numOfJob;
        this.like = like;
        this.disLike = disLike;
        this.rating = rating;
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getNumOfJob() {
        return numOfJob;
    }

    public void setNumOfJob(int numOfJob) {
        this.numOfJob = numOfJob;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDisLike() {
        return disLike;
    }

    public void setDisLike(int disLike) {
        this.disLike = disLike;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

