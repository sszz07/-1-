package org.techtown.image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface imageboard_text_ApiInterface {
    @GET("example_select.php")
    Call<List<imageboard_text_data>> getNameHobby();

    @FormUrlEncoded
    @POST("example_insert.php")
    Call<imageboard_text_data> insertPerson(
            @Field("name") String name,
            @Field("hobby") String hobby,
            @Field("nick") String nick
    );

    @FormUrlEncoded
    @POST("like_insert.php")
    Call<imageboard_text_data> insert_like(
            @Field("like_name") String like_name,
            @Field("like_hobby") String like_hobby,
            @Field("like_nick") String nick
    );

    @GET("like_select.php")
    Call<List<imageboard_text_data>> getlike();


    @FormUrlEncoded
    @POST("example_update.php")
    Call<imageboard_text_data> updatePerson(
            @Field("id") int id,
            @Field("name") String name,
            @Field("hobby") String hobby,
            @Field("nick") String nick
    );

    @FormUrlEncoded
    @POST("example_delete.php")
    Call<imageboard_text_data> deletePerson(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("like_delete.php")
    Call<imageboard_text_data> deletelike(
            @Field("like_name") String like_name
    );


}
