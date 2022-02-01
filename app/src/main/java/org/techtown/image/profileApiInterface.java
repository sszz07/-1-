package org.techtown.image;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface profileApiInterface {

    @FormUrlEncoded
    @POST("user_edit.php")
    Call<profile_data> performUser_edit(@Field("user_email") String user_email, @Field("user_nick") String user_nick,
                                        @Field("user_image") String user_image);




    @FormUrlEncoded
    @POST("user_select.php")
    //@Field을 하는이유는 서버에서 잘 받아와야 되기 때문이다
    Call<profile_data> getuserdatacallback(@Field("user_email") String user_email, @Field("user_nick") String user_nick, @Field("user_image")
            String user_image);
}
