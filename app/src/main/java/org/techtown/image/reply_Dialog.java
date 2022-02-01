package org.techtown.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class reply_Dialog extends Activity {
    private EditText title;
    //버튼은 왜 변수로 설정을 하지
    private Button Ok_bnt, Cancel_bnt ,del_bnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState-받아온 데이터를 저장을 하는것
        //왜 super로 관리를 하는거지?
//        super-부모클래스의 생성자를 호출할때 사용
//                왜 생성자를 호출할까?
//                생성자는 객체를 특성을 바꿔주기 위해서 만든것인데

        super.onCreate(savedInstanceState);
        //받아온 데이터를 바꿔주기 위해서 수퍼로 받다가 크리에이트에서 관리를 시작하게 만들기 위해서 만듬
        setContentView(R.layout.activity_dialog);
        title = (EditText) findViewById(R.id.word_dialog);
        Ok_bnt = (Button) findViewById(R.id.OkButton_dialog);
        Cancel_bnt = (Button) findViewById(R.id.CancelButton_dialog);

        Intent intent = getIntent();
//        Intent intent = getIntent();-인텐트 가져오기 그래서 다이얼로그로 할때 이미지를 가져올수가 있다
        String data1 = intent.getStringExtra("제목");
//        getStringExtra(넣을 내용 이름 정하기)-넣지 않아도 되지만 ""<-는 넣어줘야 함
        title.setText(data1);
        //setText는 화면 값을 넣을수 있도록 한다
        int count = intent.getIntExtra("count", 0);





        Ok_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word_data = title.getText().toString();
                Intent intent_result = new Intent();
                intent_result.putExtra("revise_word", word_data);
                intent_result.putExtra("count" , count );
                setResult(1355, intent_result);
                finish();
            }

        });
        Cancel_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}



//리사이클러뷰 순서
//1.메인
//2.아이템
//3.어뎁터
