package com.nabeeltech.retrofitcodinginflow.Model;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    private Integer id;    //this has to be integer because if it is null it will be ignored by retrofit

    private String title;

    @SerializedName("body")
    private String text;

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}