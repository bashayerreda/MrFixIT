package com.example.fixawy.Pojos;

public class Comment {
    public String phoneOfWorker,phoneOfClient,comment,reply,jobTitle;

    public Comment(){}

    public Comment(String phoneOfWorker, String phoneOfClient, String comment, String reply) {
        this.phoneOfWorker = phoneOfWorker;
        this.phoneOfClient = phoneOfClient;
        this.comment = comment;
        this.reply = reply;
    }

    public String getPhoneOfWorker() {
        return phoneOfWorker;
    }

    public void setPhoneOfWorker(String phoneOfWorker) {
        this.phoneOfWorker = phoneOfWorker;
    }

    public String getPhoneOfClient() {
        return phoneOfClient;
    }

    public void setPhoneOfClient(String phoneOfClient) {
        this.phoneOfClient = phoneOfClient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}

