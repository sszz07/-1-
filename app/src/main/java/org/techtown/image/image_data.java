package org.techtown.image;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//이미지 데이터 변수를 담는 클래스
public class image_data {

    //이미지 데이터의 변수는 message
    //status는 왜 불리언으로 되어있는지 모르겠다

    public Boolean status;
    @Expose
    @SerializedName("nickname") private int nickname;

    public int getNickname() {
        return nickname;
    }

    public void setNickname(int nickname) {
        this.nickname = nickname;
    }

    @Expose
    @SerializedName("name") private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
