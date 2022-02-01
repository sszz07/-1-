package org.techtown.image;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    ImageButton add_btn;
    RecyclerView recyclerView;
    imageboard_text_Adapter adapter;
    imageboard_text_Adapter.ItemClickListener itemClickListener;
    List<imageboard_text_data> list = new ArrayList<>();
    List<image_data> imagelist = new ArrayList<>();
    ImageView imageView, mainImage;
    imageboard_imageshow_adpater imageadapter;
    ImageButton board, man;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.person_recyclerview);
        add_btn = (ImageButton) findViewById(R.id.add_btn);


        board = findViewById(R.id.board);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, board.class);
                startActivity(intent);
            }
        });



        man = findViewById(R.id.man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        selectPerson();
        itemClickListener = new imageboard_text_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getId();
                String name = list.get(position).getName();
                Log.e("네임 데이터 확인", name);
                String hobby = list.get(position).getHobby();
                Log.e("취미 데이터 확인", hobby);
                String nick = list.get(position).getNick();
                Log.e("닉네임 데이터 확인", nick);



                Log.e(TAG, "id : " + id + ", name : " + name + ", hobby : " + hobby);
                Intent intent = new Intent(MainActivity.this, imageboard_select.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("hobby", hobby);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        };

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, imageboard.class);
                startActivity(intent);
            }
        });
    }





    //------------------------------------------------------------------------------------------------------------------------------------------
    //글을 가지고 올수있는 메소드
    private void selectPerson() {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        Call<List<imageboard_text_data>> call = imageboard_text_ApiInterface.getNameHobby();
        call.enqueue(new Callback<List<imageboard_text_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<imageboard_text_data>> call, @NonNull Response<List<imageboard_text_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());


                }
            }

            @Override
            public void onFailure(@NonNull Call<List<imageboard_text_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<imageboard_text_data> lists) {
        adapter = new imageboard_text_Adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------------------------------
    //이미지를 가져오는 함수
//    private void getuserdatacallback(String name) {
//        imageboard_image_ApiInterface imageboardimageApiInterface = imageboard_image_ApiClient.getClient().create(imageboard_image_ApiInterface.class);
//        Call<List<image_data>> call = imageboardimageApiInterface.getimage(name);
//        call.enqueue(new Callback<List<image_data>>() {
//            @Override
//            public void onResponse(Call<List<image_data>> call, Response<List<image_data>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                } else {
//                    //이미지를 넣기전에 데이터값을 가지고 올수있도록 만들기!!!!!!
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<image_data>> call, Throwable t) {
//
//            }
//        });
//    }
//------------------------------------------------------------------------------------------------------------------------------------------

}