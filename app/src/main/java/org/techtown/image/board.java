package org.techtown.image;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class board extends AppCompatActivity
{
    public static final String TAG = "MainActivity";

    NestedScrollView nestedScrollView;
    EditText edit_name, edit_hobby;
    ImageButton write;
    String name, hobby;
    RecyclerView recyclerView;
    boardPersonAdapter adapter;
    boardPersonAdapter.ItemClickListener itemClickListener;
    List<boardPerson> list = new ArrayList<>();
    ProgressBar progressBar;
    Menu main_menu;
    int page = 0, limit = 10;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        write = findViewById(R.id.add_btn1);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), boardwrite.class);
                startActivity(intent);
            }
        });

        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) findViewById(R.id.person_recyclerview);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_hobby = (EditText) findViewById(R.id.edit_hobby);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


        selectPerson(page,limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    limit=limit+10;
                    Log.e("페이징 확인", String.valueOf(page));
                    Log.e("리미트 확인", String.valueOf(limit));
                    //View.GONE-버튼 동작이 되지 않는다
                    //View.INVISIBLE-화면에서는 사라지지만 투명하게 보인다
                    selectPerson(page,limit);
                }
            }
        });

        itemClickListener = new boardPersonAdapter.ItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                int id = list.get(position).getId();
                String name = list.get(position).getName();
                String hobby = list.get(position).getHobby();
                Log.e(TAG, "id : " + id + ", name : " + name + ", hobby : " + hobby);
                Intent intent = new Intent(board.this, boardOtherActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("hobby", hobby);
                startActivity(intent);
            }
        };

    }









    private void selectPerson(int page,int limit)
    {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<List<boardPerson>> call = boardApiInterface.getNameHobby(page,limit);
        call.enqueue(new Callback<List<boardPerson>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<boardPerson>> call, @NonNull Response<List<boardPerson>> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<boardPerson>> call, @NonNull Throwable t)
            {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }










    private void onGetResult(List<boardPerson> lists)
    {
        adapter = new boardPersonAdapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        list = lists;
    }







    private void insertPerson(String name, String hobby)
    {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<boardPerson> call = boardApiInterface.insertPerson(name, hobby);
        call.enqueue(new Callback<boardPerson>()
        {
            @Override
            public void onResponse(@NonNull Call<boardPerson> call, @NonNull Response<boardPerson> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    Boolean success = response.body().getSuccess();
                    if (success)
                    {
                        onSuccess(response.body().getMessage());
                    }
                    else
                    {
                        onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<boardPerson> call, @NonNull Throwable t)
            {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }







    private void onError(String message)
    {
        Log.e("insertPerson()", "onResponse() 에러 : " + message);
    }






    private void onSuccess(String message)
    {
        Log.e("insertPerson()", "onResponse() 성공 : " + message);
        setResult(RESULT_OK);
        finish();
    }





}

