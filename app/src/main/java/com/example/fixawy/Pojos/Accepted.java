package com.example.fixawy.Pojos;

public class Accepted {
    String nameOfWorker;
    String addressOfWorker;
    String phoneOfWorker;
    String commentLine;
    String numOfJob;
    String rating;
    String numOfLike;
    String numOfDisLike;
    String image;
    String jobTitle;
    String tokenid;


    public Accepted() {
    }


    public Accepted(String nameOfWorker, String addressOfWorker, String phoneOfWorker, String commentLine, String numOfJob, String rating, String numOfLike, String numOfDisLike, String image) {
        this.nameOfWorker = nameOfWorker;
        this.addressOfWorker = addressOfWorker;
        this.phoneOfWorker = phoneOfWorker;
        this.commentLine = commentLine;
        this.numOfJob = numOfJob;
        this.rating = rating;
        this.numOfLike = numOfLike;
        this.numOfDisLike = numOfDisLike;
        this.image = image;
    }


    public Accepted(String nameOfWorker, String addressOfWorker, String phoneOfWorker, String commentLine, String numOfJob, String rating, String numOfLike, String numOfDisLike, String image, String jobTitle, String tokenid) {
        this.nameOfWorker = nameOfWorker;
        this.addressOfWorker = addressOfWorker;
        this.phoneOfWorker = phoneOfWorker;
        this.commentLine = commentLine;
        this.numOfJob = numOfJob;
        this.rating = rating;
        this.numOfLike = numOfLike;
        this.numOfDisLike = numOfDisLike;
        this.image = image;
        this.jobTitle = jobTitle;
        this.tokenid = tokenid;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
}
