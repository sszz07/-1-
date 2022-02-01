package org.techtown.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class imageboard_text_data {


    @Expose
    @SerializedName("id")
    private int id;


    @Expose
    @SerializedName("idx")
    private int idx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    @Expose
    @SerializedName("like_nick")
    private String like_nick;

    public String getLike_nick() {
        return like_nick;
    }

    public void setLike_nick(String like_nick) {
        this.like_nick = like_nick;
    }

    @Expose
    @SerializedName("like_name")
    private String like_name;

    public String getLike_name() {
        return like_name;
    }

    public void setLike_name(String like_name) {
        this.like_name = like_name;
    }

    public String getLike_hobby() {
        return like_hobby;
    }

    public void setLike_hobby(String like_hobby) {
        this.like_hobby = like_hobby;
    }

    @Expose
    @SerializedName("like_hobby")
    private String like_hobby;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("nick")
    private String nick;

    @Expose
    @SerializedName("hobby")
    private String hobby;

    @Expose
    @SerializedName("success")
    private Boolean success;

    @Expose
    @SerializedName("message")
    private String message;


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}