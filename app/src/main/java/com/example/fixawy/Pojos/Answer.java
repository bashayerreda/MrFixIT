package com.example.fixawy.Pojos;

public class Answer {
    public String replay,phone,clientQuestion,phoneOfClient;

    public Answer(String replay) {

        this.replay = replay;
    }

    public Answer(String replay, String phone, String clientQuestion, String phoneOfClient) {
        this.replay = replay;
        this.phone = phone;
        this.clientQuestion = clientQuestion;
        this.phoneOfClient = phoneOfClient;
    }

    public Answer(String replay, String phone) {
        this.replay = replay;
        this.phone = phone;
    }

    public Answer(String replay, String phone, String clientQuestion) {
        this.replay = replay;
        this.phone = phone;
        this.clientQuestion = clientQuestion;
    }

    public String getPhoneOfClient() {
        return phoneOfClient;
    }

    public void setPhoneOfClient(String phoneOfClient) {
        this.phoneOfClient = phoneOfClient;
    }

    public String getClientQuestion() {
        return clientQuestion;
    }

    public void setClientQuestion(String clientQuestion) {
        this.clientQuestion = clientQuestion;
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

    public Answer() {
    }

}

