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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageobaord_Update extends AppCompatActivity
{
    public static final String TAG = "UpdateActivity";

    EditText name_edittext, hobby_edittext,nick_edittext;
    Button update_done,changeimagebutton;
    int id;
    String name, hobby,nick;
    private ArrayList<Uri> images = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    ImageView imageView;
    RecyclerView recyclerView;
    List<image_data> list = new ArrayList<>();
    imageboard_imageshow_adpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageboard_update);
        imageView = (ImageView) findViewById(R.id.imageView);
        name_edittext = (EditText) findViewById(R.id.name_edittext);
        hobby_edittext = (EditText) findViewById(R.id.hobby_edittext);
        nick_edittext = (EditText) findViewById(R.id.nick_edittext);
        update_done = (Button) findViewById(R.id.update_done);
        recyclerView= findViewById(R.id.change_image);
        changeimagebutton = (Button) findViewById(R.id.changeadd);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        hobby = intent.getStringExtra("hobby");
        nick = intent.getStringExtra("nick");
        Log.e(TAG, "수정 id : " + id + ", 수정 이름 : " + name + ", 수정 취미 : " + hobby);

        name_edittext.setText(name);
        hobby_edittext.setText(hobby);
        nick_edittext.setText(nick);


        getuserdatacallback(name);
        imageboard_imageshow_adpater adapter = new imageboard_imageshow_adpater(imageobaord_Update.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);


        update_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                uploadToServer();
                updatePerson(id, name, hobby,nick);
                Toast.makeText(imageobaord_Update.this, "수정 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(imageobaord_Update.this, MainActivity.class);
                startActivity(intent);
            }
        });


        changeimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, CODE_ALBUM_REQUEST);
            }
        });
    }


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
                        String imagePath = imageboard_Utils.getPath(imageobaord_Update.this,image);
                        images.add(Uri.parse(imagePath));
                    }
                }
            }
            //리사이클러뷰에 보여주기

            Imageboard_ListAdapter adapter = new Imageboard_ListAdapter(imageobaord_Update.this,images);
            recyclerView.setAdapter(adapter);
        }
    } //end of onActivityResult

//이미지 서버로 보내기
    public void uploadToServer(){

        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for(Uri uri: images){
            list.add(prepairFiles("file[]", uri));
        }

        imageboard_image_ApiInterface service = imageboard_image_ApiClient.getClient().create(imageboard_image_ApiInterface.class);
        Call<image_data> call = service.changeimage(list);
        call.enqueue(new Callback<image_data>() {
            @Override
            public void onResponse(Call<image_data> call, Response<image_data> response) {
                image_data model = response.body();
            }

            @Override
            public void onFailure(Call<image_data> call, Throwable t) {
                Toast.makeText(imageobaord_Update.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri){
        File file = new File( fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return  MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }



    private void updatePerson(int id, String name, String hobby,String nick)
    {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        name = name_edittext.getText().toString();
        hobby = hobby_edittext.getText().toString();
        nick = nick_edittext.getText().toString();
        Call<imageboard_text_data> call = imageboard_text_ApiInterface.updatePerson(id, name, hobby,nick);
        call.enqueue(new Callback<imageboard_text_data>()
        {
            @Override
            public void onResponse(@NonNull Call<imageboard_text_data> call, @NonNull Response<imageboard_text_data> response)
            {

            }

            @Override
            public void onFailure(@NonNull Call<imageboard_text_data> call, @NonNull Throwable t)
            {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
            }
        });
    }

    //이미지 값 가져오기
//이미지 값 가져오기
    private void getuserdatacallback(String name) {
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

}