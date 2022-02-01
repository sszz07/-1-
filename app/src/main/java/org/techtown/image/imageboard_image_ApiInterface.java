package org.techtown.image;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface imageboard_image_ApiInterface {

    @Multipart
    @POST("multi_upload.php")
    Call<image_data> callMultipleUploadApi(@Part List<MultipartBody.Part> image);


//    @GET("get_images.php")
//    Call<FileModel> getimage(@Field("name") String name,@Field("nickname") String nickname);

    @GET("get_images.php")
    Call<List<image_data>> getimage(
            @Query("name") String name
    );


    @FormUrlEncoded
    @POST("delete_images.php")
    Call<image_data> deleteimage(
            @Field("nickname") int id
    );

    @Multipart
    @POST("change_images.php")
    Call<image_data> changeimage(@Part List<MultipartBody.Part> image);
}


