package org.techtown.image;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {

    // 정보 적는변수
    TextView tv_user_email;
    EditText et_user_nick, et_user_password;
    // 버튼 변수
    Button btn_user_edit, btn_user_edit_password, btn_user_delete,좋아요;
    // 뒤로가기 변수
    ImageButton ib_back;
    // 이미지 선택
    ImageView iv_users;
    //    ImageView iv_users;
    // 이미지 가지고 오기
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 레이아웃 연결
        tv_user_email = findViewById(R.id.tv_user_email);
        et_user_nick = findViewById(R.id.et_user_nick);
        et_user_password = findViewById(R.id.et_user_password);
        btn_user_edit = findViewById(R.id.btn_user_edit);
        btn_user_delete = findViewById(R.id.btn_user_delete);
        iv_users = findViewById(R.id.iv_users);

        // String으로 변환
        String user_email = getIntent().getStringExtra("user_email");
        String user_nick = getIntent().getStringExtra("user_nick");
        String user_password = getIntent().getStringExtra("user_password");
        String user_image = getIntent().getStringExtra("user_image");
        String user_idx = getIntent().getStringExtra("user_idx");
//        Log.e("회원정보 수정 1 ","정보 가져오는 곳 : " + user_idx);
        // 정보 출력하는곳

        tv_user_email.setText(user_email);
        et_user_nick.setText(user_nick);
        et_user_password.setText(user_password);

        // 이미지 불러오기

        getuserdatacallback();
        tv_user_email.setText("sszz07@naver.com");


        //  버튼눌러서 픽사베이 이미지 가지고 오는 부분
        btn_user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        // 수정버튼 클릭시 실행
        btn_user_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메소드 실행
                performEdit();
            }
        });

        좋아요 = findViewById(R.id.좋아요);
        좋아요.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, like.class);
                startActivity(intent);
            }
        });
    } // onCreate 끝


    // 서버로 데이터 전달
    private void performEdit() {
        String user_email = tv_user_email.getText().toString();
        String user_nick = et_user_nick.getText().toString();
        String user_password = et_user_password.getText().toString();
        String user_image = imageToString();
        Log.e("서버로 이메일 보내기 정보 : ",user_email);
//        manApiClient객체를 를 붙이고 서버로 보낼때 제이슨 형태로 보내야 된다
//        manApiClient.getApiClient().create(manApiInterface.class)--> 매개변수는 제이슨의 형태로 바꾸기 위한것이다
        Call<profile_data> call = profileApiClient.getApiClient().create(profileApiInterface.class).performUser_edit(user_email, user_nick, user_image);
        call.enqueue(new Callback<profile_data>() {
            @Override
            public void onResponse(Call<profile_data> call, Response<profile_data> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {

                        if (response.body().getResultCode() == 1) {

                        } else {
                            Toast.makeText(getApplicationContext(), "회원정보 변경 실패1", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "회원정보 변경 실패2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "회원정보 변경 실패3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<profile_data> call, Throwable t) {
//                Log.e("회원정보 수정 통신 ","통신 실패 : "+t.toString());
//                Log.e("User_editActivity 9 ","fail"+t.toString());
                System.out.println(t.getMessage());
            }
        });
    } // performEdit메소드 끝


    private void getuserdatacallback() {
        String user_email = tv_user_email.getText().toString();
        Log.e("이메일 값 확인",user_email);
        String user_nick = et_user_nick.getText().toString();
        String user_image = iv_users.toString();

        profileApiInterface profileApiInterface = profileApiClient.getApiClient().create(profileApiInterface.class);
        Call<profile_data> call = profileApiInterface.getuserdatacallback(user_email, user_nick, user_image);
        call.enqueue(new Callback<profile_data>() {
            @Override
            public void onResponse(@NonNull Call<profile_data> call, @NonNull Response<profile_data> response) {


                if (response.isSuccessful() && response.body() != null) {

                    String user_nick = response.body().getUser_nick();
                    String user_image = response.body().getUser_image();

//
//                    Log.e("이미지 들어가있는지 확인12323", user_nick);
//                    Log.e("이미지 들어가있는지 확인", user_image);
                    et_user_nick.setText(user_nick);

                    if (response.body().getUser_image() != null) {
                        Glide.with(getApplicationContext())
                                .load("http://52.79.180.89/" + user_image)// image url
                                .into(iv_users);
                    }
                }
            }

            @Override
            public void onFailure(Call<profile_data> call, Throwable t) {

            }

        });

    }


    // 이미지 선택
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    // 이미지 받는곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                iv_users.setImageBitmap(bitmap);
                iv_users.setVisibility(View.VISIBLE);
                tv_user_email.setVisibility(View.VISIBLE);
                iv_users.setEnabled(false);
                btn_user_edit.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 이미지 보내는곳
    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);

    }
}

