package org.techtown.image;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profileApiClient {
    //내 서버의 주소
    private static final String BASE_URL = "http://52.79.180.89/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient()
    {
        //gson왜 만드냐면 서버에서 값이 넘어올때 제이슨 형태로 온다 그래서 자바에서 값을 객체로 만들어줘야 된다
        /**
         * 1.제이슨객체를 만들어준다
         * GsonBuilder()-몇가지 옵션을 추가해서 객체를 생성을 할수가 있다 어떤 옵션이냐면 밑에 setLenient,create등 붙여서 만들수가 있다 vs Gson()-안된다
         *
         * 2.setLenient()-값이 잘못되었을때 오류가 발생하게 만든다
         * 3.3.create()-밑에 gson의 변수를 만들어줌
         */
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();



        if (retrofit == null)
        {
            /**
             * 1.
             *
             *
             * .addConverterFactory()-Json형식의 파일을 나중에 만들 POJO Class(데이터 변수가 있는 클래스) 형식으로 자동으로 변환하여 줍니다
             * (GsonConverterFactory.create(gson)-지슨으로 받아온 데이터를 제이슨 형태받은 값
             * Builder()-값을 쌓아 올린다
             * 쌓아 올린것을 retrofit변수에 넣어지게 된다
             */
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
