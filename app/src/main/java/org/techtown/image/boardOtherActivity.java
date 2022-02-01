package org.techtown.image;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class boardOtherActivity extends AppCompatActivity
{
    public static final String TAG = "OtherActivity";

    TextView other_name, other_hobby;
    Button update_btn, delete_btn,댓글;
    int id;
    String name, hobby;
    private ArrayList<reply_item> memo;
    private reply_Adapter aAdpater;
    private static int count;
    private String title;
    private String content;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    Button 수정,삭제;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        other_name = (TextView) findViewById(R.id.other_name1);
        other_hobby = (TextView) findViewById(R.id.other_hobby1);
        update_btn = (Button) findViewById(R.id.update_btn1);
        delete_btn = (Button) findViewById(R.id.delete_btn1);




        //수정할때 데이터를 받는곳
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        hobby = intent.getStringExtra("hobby");
        Log.e(TAG, "인텐트 id : " + id + ", 인텐트 이름 : " + name + ", 인텐트 취미 : " + hobby);
        other_name.setText(name);
        other_hobby.setText(hobby);





        update_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent update_intent = new Intent(boardOtherActivity.this, boardUpdateActivity.class);
                update_intent.putExtra("id", id);
                update_intent.putExtra("name", name);
                update_intent.putExtra("hobby", hobby);
                startActivity(update_intent);
            }
        });






        delete_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 삭제 메서드
                deletePerson(id);
                Intent delete_intent = new Intent(boardOtherActivity.this, board.class);
                startActivity(delete_intent);
            }
        });


//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_word2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        memo = new ArrayList<>();
        aAdpater = new reply_Adapter(memo);
        recyclerView.setAdapter(aAdpater);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("클릭이벤트", "아이템 터치 리스너 온클릭" + position);
            }

            @Override
            public void onLongClick(View view, int position) {  //수정액티비티 다이얼로그
                Intent intent = new Intent(boardOtherActivity.this, reply_Dialog.class);
                intent.putExtra("제목", memo.get(position).getTitle());
                intent.putExtra("count", position);
                startActivityForResult(intent, 1107);
            }

        }));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
//        Button b = (Button) findViewById(R.id.댓글);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(boardOtherActivity.this, a_Dialog.class);
//                startActivityForResult(intent, 1);
//                Log.d("클릭이벤트", "추가하기");
//            }
//        });


//        try-catch 문-예외처리
//        sharedPreferences-저장관리 하는것
//        sharedPreferences-string in boolean으로 저장이 안됨
//        MODE_PRIVATE-값을 현재 어플에만 저장을 하겠다
        try {
            sharedPreferences = getSharedPreferences("메모장", MODE_PRIVATE);
            String JsonArrayData = sharedPreferences.getString("메모", "");
            JSONArray ja = new JSONArray(JsonArrayData);


            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);
                String title = order.getString("word");
                reply_item data = new reply_item(title + "");
                memo.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent_result) {
        super.onActivityResult(requestCode, resultCode, intent_result);

        if (requestCode == 1) {
            if (resultCode != 1355) {
                return;
            }
            String get_key = intent_result.getExtras().getString("key");
            title = intent_result.getExtras().getString("revise_word");
            reply_item data = new reply_item(title + "");
            memo.add(data);
            aAdpater.notifyDataSetChanged();
        } else if (requestCode == 1107) {
            if (resultCode != 1355) {
                return;
            }
            title = intent_result.getExtras().getString("revise_word");
            count = intent_result.getIntExtra("count", count);
            reply_item data = new reply_item(title + "");
            memo.set(count, data);
            aAdpater.notifyDataSetChanged();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {


        super.onResume();


    }

    public interface ClickListener {


        void onClick(View view, int position);

        void onLongClick(View view, int position);


    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        try {
            sharedPreferences = getSharedPreferences("메모장", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < memo.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("word", memo.get(i).getTitle());
                jArray.put(sObject);

            }
            editor.putString("메모", String.valueOf(jArray));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    private void deletePerson(int id)
    {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<boardPerson> call = boardApiInterface.deletePerson(id);
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
                Log.e("deletePerson()", t.getMessage());
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