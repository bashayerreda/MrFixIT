package com.example.fixawy.Pojos;

public class WorkersAccepted {

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

    String nameOfWorker;
    String addressOfWorker;
    String phoneOfWorker;
    String commentLine;
    String numOfJob;
    String rating;
    String numOfLike;
    String numOfDisLike;
    String image;

    String tokenid;
    String clientTokenId;

    public WorkersAccepted(){}

    public WorkersAccepted(String time, String date, String typeOfOrder, String location, String requestedPhone, String jobTitle, String userName, String nameOfWorker, String addressOfWorker, String phoneOfWorker, String numOfJob, String rating, String numOfLike, String numOfDisLike, String image,String tokenid,String clientTokenId) {
        this.time = time;
        this.date = date;
        this.typeOfOrder = typeOfOrder;
        this.location = location;
        this.requestedPhone = requestedPhone;
        this.jobTitle = jobTitle;
        this.userName = userName;
        this.nameOfWorker = nameOfWorker;
        this.addressOfWorker = addressOfWorker;
        this.phoneOfWorker = phoneOfWorker;
        this.numOfJob = numOfJob;
        this.rating = rating;
        this.numOfLike = numOfLike;
        this.numOfDisLike = numOfDisLike;
        this.image = image;
        this.tokenid = tokenid;
        this.clientTokenId = clientTokenId;
    }

    public String getClientTokenId() {
        return clientTokenId;
    }

    public void setClientTokenId(String clientTokenId) {
        this.clientTokenId = clientTokenId;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
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

    public String getNameOfWorker() {
        return nameOfWorker;
    }

    public void setNameOfWorker(String nameOfWorker) {
        this.nameOfWorker = nameOfWorker;
    }

    public String getAddressOfWorker() {
        return addressOfWorker;
    }

    public void setAddressOfWorker(String addressOfWorker) {
        this.addressOfWorker = addressOfWorker;
    }

    public String getPhoneOfWorker() {
        return phoneOfWorker;
    }

    public void setPhoneOfWorker(String phoneOfWorker) {
        this.phoneOfWorker = phoneOfWorker;
    }

    public String getCommentLine() {
        return commentLine;
    }

    public void setCommentLine(String commentLine) {
        this.commentLine = commentLine;
    }

    public String getNumOfJob() {
        return numOfJob;
    }

    public void setNumOfJob(String numOfJob) {
        this.numOfJob = numOfJob;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(String numOfLike) {
        this.numOfLike = numOfLike;
    }

    public String getNumOfDisLike() {
        return numOfDisLike;
    }

    public void setNumOfDisLike(String numOfDisLike) {
        this.numOfDisLike = numOfDisLike;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
