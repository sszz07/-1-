package org.techtown.image;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class boardUpdateActivity extends AppCompatActivity
{
    public static final String TAG = "UpdateActivity";

    EditText name_edittext, hobby_edittext;
    Button update_done;
    int id;
    String name, hobby;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_edittext = (EditText) findViewById(R.id.name_edittext);
        hobby_edittext = (EditText) findViewById(R.id.hobby_edittext);
        update_done = (Button) findViewById(R.id.update_done);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        hobby = intent.getStringExtra("hobby");
        Log.e(TAG, "수정 id : " + id + ", 수정 이름 : " + name + ", 수정 취미 : " + hobby);

        name_edittext.setText(name);
        hobby_edittext.setText(hobby);

        update_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updatePerson(id, name, hobby);
                Toast.makeText(boardUpdateActivity.this, "수정 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(boardUpdateActivity.this, board.class);
                startActivity(intent);
            }
        });
    }

    private void updatePerson(int id, String name, String hobby)
    {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        name = name_edittext.getText().toString();
        hobby = hobby_edittext.getText().toString();
        Call<boardPerson> call = boardApiInterface.updatePerson(id, name, hobby);
        call.enqueue(new Callback<boardPerson>()
        {
            @Override
            public void onResponse(@NonNull Call<boardPerson> call, @NonNull Response<boardPerson> response)
            {
                //
            }

            @Override
            public void onFailure(@NonNull Call<boardPerson> call, @NonNull Throwable t)
            {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
            }
        });
    }

}