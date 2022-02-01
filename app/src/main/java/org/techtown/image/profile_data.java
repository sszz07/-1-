package org.techtown.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// DTP(pojo): (Data Transfer Object)||(Plain Old Java Object)형태의 모델(Model)/json 타입변환에 사용
public class profile_data {

    // Gson은 자바 객체와 JSON 간의 직렬화 및 역직렬화를 위한 오픈소스 라이브러리
    // @SerializedName("")을 붙여 원래 변수명을 넣어주고 그 밑에 변수를 선언할 떄는 원하는 변수명을 사용하면 된다.
    // @SerializedName은 Gson라이브러리에서 제공하는 어노테이션인데 여기서 Serialize는 data class객체를 JSON형태로 변환하는 것을 말한다. (한국말로는 직렬화이다)
    // user_email == 직렬화 이름

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("user_email")
    private String user_email;


    @SerializedName("user_nick")
    private String user_nick;

    @SerializedName("user_idx")
    private String user_idx;

    @Expose
    @SerializedName("user_image")
    private String user_image;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public String getUser_nick() {
        return user_nick;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }


    // toString()을 Override 해주지 않으면 객체 주소값을 출력함

//    @Override
//    public String toString() {
//        Log.e("유저 DTP ", "순서확인 : " + status + resultCode + user_nick);
//        return "ApiResponse{" +
//                "status='" + status + '\'' +
//                ", resultCode=" + resultCode +
//                ", user_nick=" + user_nick +
//                '}';
//    }
}
