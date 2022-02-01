package org.techtown.image;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class like extends AppCompatActivity {
    public static final String TAG = "like";
    RecyclerView recyclerView;
    imageboard_like_text_Adapter adapter;
    imageboard_like_text_Adapter.ItemClickListener itemClickListener;
    List<imageboard_text_data> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        recyclerView = (RecyclerView) findViewById(R.id.re_like);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


        selectPerson();
        itemClickListener = new imageboard_like_text_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getIdx();
                String name = list.get(position).getLike_name();

//                Log.e("네임 데이터 확인", name);
                String hobby = list.get(position).getLike_hobby();
//                Log.e("취미 데이터 확인", hobby);
                String nick = list.get(position).getLike_nick();
//                Log.e("닉네임 데이터 확인", nick);


                Log.e(TAG, "id : " + id + ", name : " + name + ", hobby : " + hobby+"nick:"+nick);
                Intent intent = new Intent(like.this, imageboard_like_select.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("hobby", hobby);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        };


    }

    //데이터를 선택을 했을때
    private void selectPerson() {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        Call<List<imageboard_text_data>> call = imageboard_text_ApiInterface.getlike();
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
        adapter = new imageboard_like_text_Adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }




}