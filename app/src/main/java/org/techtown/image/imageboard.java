package org.techtown.image;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageboard extends AppCompatActivity {

    private Button btnAdd, btnSubmit;
    private RecyclerView imageList;
    private ArrayList<Uri> images = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    EditText edit_name, edit_hobby, edit_nick;
    Button add_btn1;
    String name, hobby, nick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageboard);

        btnAdd = findViewById(R.id.add);
        imageList= findViewById(R.id.image_list);
        imageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        add_btn1 = findViewById(R.id.btn_upload);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_hobby = (EditText) findViewById(R.id.edit_hobby);
        edit_nick = (EditText) findViewById(R.id.edit_nick);

        //버튼 클릭했을 때 갤러리 연다
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, CODE_ALBUM_REQUEST);
            }
        });


        add_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edit_name.getText().toString();
                hobby = edit_hobby.getText().toString();
                nick = edit_nick.getText().toString();
                insertPerson(name, hobby, nick);
                uploadToServer();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });

    }//온크리에이트 끝



    //앨범 이미지 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //갤러리 이미지 가져오기
        if (requestCode == CODE_ALBUM_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                if (clipData.getItemCount() > 10) { // 10개 초과하여 이미지를 선택한 경우
                    return;
                }  else if (clipData.getItemCount() > 1 && clipData.getItemCount() <= 10) { //1개초과  10개 이하의 이미지선택한 경우
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri image = data.getClipData().getItemAt(i).getUri();
                        String imagePath = imageboard_Utils.getPath(imageboard.this,image);
                        images.add(Uri.parse(imagePath));
                    }
                }
            }
            //리사이클러뷰에 보여주기

            Imageboard_ListAdapter adapter = new Imageboard_ListAdapter(imageboard.this,images);
            imageList.setAdapter(adapter);
        }
    } //end of onActivityResult

    //이미지서버에 보내는 함수
    public void uploadToServer(){

        ArrayList<MultipartBody.Part> list = new ArrayList<>();
       for(Uri uri: images){
           list.add(prepairFiles("file[]", uri));
       }

        imageboard_image_ApiInterface service = imageboard_image_ApiClient.getClient().create(imageboard_image_ApiInterface.class);
        Call<image_data> call = service.callMultipleUploadApi(list);
        call.enqueue(new Callback<image_data>() {
            @Override
            public void onResponse(Call<image_data> call, Response<image_data> response) {
                image_data model = response.body();
            }

            @Override
            public void onFailure(Call<image_data> call, Throwable t) {
                Toast.makeText(imageboard.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri){
        File file = new File( fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return  MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private void insertPerson(String name, String hobby, String nick) {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        Call<imageboard_text_data> call = imageboard_text_ApiInterface.insertPerson(name, hobby, nick);
        call.enqueue(new Callback<imageboard_text_data>() {
            @Override
            public void onResponse(@NonNull Call<imageboard_text_data> call, @NonNull Response<imageboard_text_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<imageboard_text_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
}