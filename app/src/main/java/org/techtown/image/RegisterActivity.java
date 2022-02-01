package org.techtown.image;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText, et_pass, et_name, et_age,emailCodeText;
    private Button btn_register,Button;
    MainHandler mainHandler;
    TextView textView;
    Button emailCodeButton;

    String GmailCode;
    static int value;
    int mailSend = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        emailText = findViewById(R.id.emailText);
        et_pass = findViewById(R.id.et_pass1);
        et_name = findViewById(R.id.et_name1);
        et_age = findViewById(R.id.et_age1);

        emailCodeText = findViewById(R.id.emailCodeText);
        emailCodeText.setVisibility(View.GONE);

        emailCodeButton = findViewById(R.id.emailCodeButton);
        emailCodeButton.setVisibility(View.GONE);

        textView = findViewById(R.id.textView1);
        Button = findViewById(R.id.Button);

        //이메일 인증 하는 부분
        //인증코드 시간초가 흐르는데 이때 인증을 마치지 못하면 인증 코드를 지우게 만든다.
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                이메일 인증부분을 보여준다.

                //메일을 보내주는 쓰레드
                MailTread mailTread = new MailTread();
                mailTread.start();

                if (mailSend == 0) {
                    value = 180;
                    //쓰레드 객체 생성
                    BackgrounThread backgroundThread = new BackgrounThread();
                    //쓰레드 스타트
                    backgroundThread.start();
                    mailSend += 1;
                } else {
                    value = 180;
                }


                //이메일이 보내지면 이 부분을 실행시킨다.
                emailCodeText.setVisibility(View.VISIBLE);
                emailCodeButton.setVisibility(View.VISIBLE);
//핸들러 객체 생성
                mainHandler = new MainHandler();
            }
        });


        //인증하는 버튼이다
        //혹시 이거랑 같으면 인증을 성공시켜라라
        emailCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                //이메일로 전송한 인증코드와 내가 입력한 인증코드가 같을 때
                if (emailCodeText.getText().toString().equals(GmailCode)) {
                    Toast.makeText(getApplicationContext(), "인증되었습니다", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "인증번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register12);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = emailText.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String userAge = et_age.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(userID,userPass,userName,userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });

    }

    //메일 보내는 쓰레드
    class MailTread extends Thread {

        public void run() {
            GMailSender gMailSender = new GMailSender("sg4771089@gmail.com", "qwe123@@");
            //GMailSender.sendMail(제목, 본문내용, 받는사람);


            //인증코드
            GmailCode = gMailSender.getEmailCode();
            try {
                gMailSender.sendMail("인증번호", GmailCode, emailText.getText().toString());
            } catch (SendFailedException e) {

            } catch (MessagingException e) {
                System.out.println("인터넷 문제" + e);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //시간초가 카운트 되는 쓰레드
    class BackgrounThread extends Thread {
        //180초는 3분
        //메인 쓰레드에 value를 전달하여 시간초가 카운트다운 되게 한다.

        public void run() {
            //180초 보다 밸류값이 작거나 같으면 계속 실행시켜라
            while (true) {
                value -= 1;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }

                Message message = mainHandler.obtainMessage();
                //메세지는 번들의 객체 담아서 메인 핸들러에 전달한다.
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);

                //핸들러에 메세지 객체 보내기기

                mainHandler.sendMessage(message);

                if (value <= 0) {
                    GmailCode = "";
                    break;
                }
            }


        }
    }






    //쓰레드로부터 메시지를 받아 처리하는 핸들러
    //메인에서 생성된 핸들러만이 Ui를 컨트롤 할 수 있다.
    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int min, sec;

            Bundle bundle = message.getData();
            int value = bundle.getInt("value");

            min = value / 60;
            sec = value % 60;
            //초가 10보다 작으면 앞에 0이 더 붙어서 나오도록한다.
            if (sec < 10) {
                //텍스트뷰에 시간초가 카운팅
                emailCodeText.setHint("0" + min + " : 0" + sec);
            } else {
                emailCodeText.setHint("0" + min + " : " + sec);
            }
        }
    }



}
