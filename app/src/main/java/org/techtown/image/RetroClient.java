package org.techtown.image;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//66666
public class RetroClient {
    private static final String BASE_URL="http://52.79.180.89/";
    private static RetroClient myClient;
    private Retrofit retrofit;

    private RetroClient(){
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetroClient getInstance(){
        if (myClient==null){
            myClient=new RetroClient();
        }
        return myClient;
    }

    public boardApiInterface getApi(){
        return retrofit.create(boardApiInterface.class);

    }
}

