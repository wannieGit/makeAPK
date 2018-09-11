package com.example.wtyul.singupandin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewFlipper Vf;
    Button Btnjoin, Btnlogin;
    EditText inputID, inputPW;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // 강제적으로 네트워크 접속

        Btnjoin = (Button)findViewById(R.id.btn_signup);
        Btnlogin = (Button)findViewById(R.id.btn_signin);
        inputID = (EditText)findViewById(R.id.user_id);
        inputPW = (EditText)findViewById(R.id.user_pw);

        Btnjoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignupPage.class));
            }
        }); // 회원가입 화면 Intent 처리

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog = ProgressDialog.show(MainActivity.this, "",
                        "Validating user...", true);
                //로그인버튼 누르고 잠시 기다리는 동안 출력되는 다이얼로그
                new Thread(new Runnable() {
                    public void run() {
                        Looper.prepare();
                        login();
                        Looper.loop();
                    }
                }).start();
            }
        });
    }//onCreate 끝
    void login(){
        try{
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://jellybox.org/login.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("u_id",inputID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("u_pw",inputPW.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response=httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response :" + response); //메시지 요청이 제대로 됬는지 확인용!
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("User Found")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                        //로그인에 성공하면 토스트메시지 출력하고,
                    }
                });
                startActivity(new Intent(MainActivity.this,MenuPage.class));
                //로그인 성공시 다음 화면으로 넘어감!
                finish();
            }else{
                Toast.makeText(MainActivity.this,"Login Fail", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
