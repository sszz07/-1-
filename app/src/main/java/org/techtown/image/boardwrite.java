package org.techtown.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class boardwrite extends AppCompatActivity {

    Button add;
    EditText edit_name, edit_hobby;
    String name, hobby;

    int IMG_REQUEST = 21;
    Bitmap bitmap;
    ImageView imageView;
    Button btnSelectImage;
    int page = 1, limit = 10;

    boardPersonAdapter adapter;
    boardPersonAdapter.ItemClickListener itemClickListener;
    List<boardPerson> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_hobby = (EditText) findViewById(R.id.edit_hobby);
        add = (Button) findViewById(R.id.add_btn);


        selectPerson();
        itemClickListener = new boardPersonAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getId();
                String name = list.get(position).getName();
                String hobby = list.get(position).getHobby();

            }
        };

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = edit_name.getText().toString();
                hobby = edit_hobby.getText().toString();
                insertPerson(name, hobby);
                Intent intent = new Intent(getApplicationContext(), board.class);
                startActivity(intent);

            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
                Log.e("이미지", "uploadImage()");

            }
        });

        //4444


    }


    private void selectPerson() {
        boardApiInterface boardApiInterface =boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<List<boardPerson>> call = boardApiInterface.getNameHobby(page, limit);
        call.enqueue(new Callback<List<boardPerson>>() {
            @Override
            public void onResponse(@NonNull Call<List<boardPerson>> call, @NonNull Response<List<boardPerson>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<boardPerson>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }


    private void onGetResult(List<boardPerson> lists) {
        adapter = new boardPersonAdapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        list = lists;
    }


    // ↓ 추가된 부분
    private void insertPerson(String name, String hobby) {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<boardPerson> call = boardApiInterface.insertPerson(name, hobby);
        call.enqueue(new Callback<boardPerson>() {
            @Override
            public void onResponse(@NonNull Call<boardPerson> call, @NonNull Response<boardPerson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        onSuccess(response.body().getMessage());
                    } else {
                        onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<boardPerson> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
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