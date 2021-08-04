package com.example.fixawy.Pojos;

public class Reply {
    public String phone,question,jobTitle,imageUri,replay;

    public Reply(String phone, String question, String imageUri, String replay) {
        this.phone = phone;
        this.question = question;
        this.imageUri = imageUri;
        this.replay = replay;
    }
    

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}
