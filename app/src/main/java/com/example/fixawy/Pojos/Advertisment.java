package com.example.fixawy.Pojos;

public class Advertisment {
    int imageId;

    public Advertisment(int imageURL) {
        this.imageURL = imageURL;
    }

    int imageURL;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
        this.imageURL = imageURL;
    }

    public Advertisment(int imageId, int imageURL) {
        this.imageId = imageId;
        this.imageURL = imageURL;
    }

    public Advertisment() {
    }
}
