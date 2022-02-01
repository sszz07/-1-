package org.techtown.image;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//안드와 서버의 통신하는 클래스
public class imageboard_image_ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://52.79.180.89/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}
