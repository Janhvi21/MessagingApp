package com.example.midterm_2018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTasks.idata{

    EditText et_email;
    EditText et_pswd;
    Button btn_login;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mailer");

        et_email=findViewById(R.id.et_email);
        et_pswd=findViewById(R.id.et_pswd);
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()) {
                    requestParams params = new requestParams();
                    params.addParameter("email", et_email.getText().toString())
                            . addParameter("password", et_pswd.getText().toString());

                    new AsyncTasks(MainActivity.this,params).execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login");
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    public void handleData(LoggedUser s) {
        SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token",s.token);
        editor.commit();
        if (s.getUser_email()==null) {
            Toast.makeText(getApplicationContext(),"Some Issue, Please Try Again",Toast.LENGTH_SHORT).show();
        }
        else{

            Intent i = new Intent(MainActivity.this, Inbox.class);
            i.putExtra("name",s.fname+" "+s.lname);
            startActivity(i);
        }
    }
}
