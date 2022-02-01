package org.techtown.image;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageboard_select extends AppCompatActivity {
    public static final String TAG = "imageboard_select";

    TextView other_name, other_hobby, other_nick;
    Button update_btn, delete_btn;
    int id;
    String name, hobby, nick;
    ImageView imageView;
    RecyclerView recyclerView;
    List<image_data> list = new ArrayList<>();
    imageboard_imageshow_adpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageboard_select);

        imageView = (ImageView) findViewById(R.id.imageView);
        other_name = (TextView) findViewById(R.id.other_name);
        other_hobby = (TextView) findViewById(R.id.other_hobby);
        other_nick = (TextView) findViewById(R.id.other_nick);
        update_btn = (Button) findViewById(R.id.update_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        recyclerView= findViewById(R.id.show_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        getimage(name);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        hobby = intent.getStringExtra("hobby");
        nick = intent.getStringExtra("nick");
        Log.e(TAG, "인텐트 id : " + id + ", 인텐트 이름 : " + name + ", 인텐트 취미 : " + hobby+",인텐트 닉 : " + nick);
        other_name.setText(name);
        other_hobby.setText(hobby);
        other_nick.setText(nick);


        imageboard_imageshow_adpater adapter = new imageboard_imageshow_adpater(imageboard_select.this, list);
        recyclerView.setAdapter(adapter);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent update_intent = new Intent(imageboard_select.this, imageobaord_Update.class);
                update_intent.putExtra("id", id);
                update_intent.putExtra("name", name);
                update_intent.putExtra("hobby", hobby);
                update_intent.putExtra("nick", nick);
                startActivity(update_intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 메서드
                deletePerson(id);
                deleteimage(1234);
                Intent delete_intent = new Intent(imageboard_select.this, MainActivity.class);
                startActivity(delete_intent);
            }
        });
    }




//이미지 값 가져오기
    private void getimage(String name) {
        imageboard_image_ApiInterface imageboardimageApiInterface = imageboard_image_ApiClient.getClient().create(imageboard_image_ApiInterface.class);
        Call<List<image_data>> call = imageboardimageApiInterface.getimage(name);
        call.enqueue(new Callback<List<image_data>>() {
            @Override
            public void onResponse(Call<List<image_data>> call, Response<List<image_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());


                }
            }

            @Override
            public void onFailure(Call<List<image_data>> call, Throwable t) {

            }
        });

    }

    private void onGetResult(List<image_data> lists) {
        adapter = new imageboard_imageshow_adpater(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }





    private void deleteimage(int id) {
        imageboard_image_ApiInterface imageboardimageApiInterface = imageboard_image_ApiClient.getClient().create(imageboard_image_ApiInterface.class);
        Call<image_data> call = imageboardimageApiInterface.deleteimage(id);
        call.enqueue(new Callback<image_data>() {
            @Override
            public void onResponse(@NonNull Call<image_data> call, @NonNull Response<image_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<image_data> call, @NonNull Throwable t) {
                Log.e("deletePerson()", t.getMessage());
            }
        });
    }


    private void deletePerson(int id) {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        Call<imageboard_text_data> call = imageboard_text_ApiInterface.deletePerson(id);
        call.enqueue(new Callback<imageboard_text_data>() {
            @Override
            public void onResponse(@NonNull Call<imageboard_text_data> call, @NonNull Response<imageboard_text_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<imageboard_text_data> call, @NonNull Throwable t) {
                Log.e("deletePerson()", t.getMessage());
            }
        });
    }


    private void onError(String message) {
        Log.e("insertPerson()", "onResponse() 에러 : " + message);
    }

    private void onSuccess(String message) {
        Log.e("insertPerson()", "onResponse() 성공 : " + message);
        setResult(RESULT_OK);
        finish();
    }

}