package org.techtown.image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface boardApiInterface {

    /**
     * api인터페이스는 왜 필요할까?
     * 인터페이스-조화 결부-하나로 만든다 데이터값을 이동할수 있게 만드는 클래스
     * 어떤 클래스와 조화를 이루는 걸까?
     * boardPerson 클래스와 getNameHobby()->board.java클래스를 조화를 이루고 php의 클래스를 가져올수 있게 한다
     * 순서를 보면 먼저 1.php의 클래스를 가지고 오고 2.boardPersonddml 클래스에서 데이터의 요청을 한다음 3.board클래스에 값을 넣을수 있게 한다
     * <p>
     * 1.셀렉트는 겟인데 델리트 인서트 업데이트는 왜 포스트 값일까?
     * GET : 데이터를 서버에서 얻는 행위
     * POST : 데이터를 서버에 제출하는 행위
     * <p>
     * <p>
     * Call<>--<> 안에 자료형은 JSON 데이터를 <> 안에 자료형으로 받겠다는 뜻입니다.
     * getNameHobby()--
     *
     * @FormUrlEncoded--
     * @Field()--
     */
    @GET("example_select.php")
    Call<List<boardPerson>> getNameHobby(
            @Query("page")
                    int page,
            @Query("limit")
                    int limit
    );



    @FormUrlEncoded
    @POST("example_insert.php")
    Call<boardPerson> insertPerson(
            @Field("name") String name,
            @Field("hobby") String hobby

    );

    @FormUrlEncoded
    @POST("example_update.php")
    Call<boardPerson> updatePerson(
            @Field("id") int id,
            @Field("name") String name,
            @Field("hobby") String hobby
    );

    @FormUrlEncoded
    @POST("example_delete.php")
    Call<boardPerson> deletePerson(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("example_insert.php")
    Call<boardPerson> uploadImage(
            @Field("EN_IMAGE") String encodedImage
    );
}
