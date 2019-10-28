package com.example.midterm_2018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements AsyncTasks.idata {

    EditText et_first_name;
    EditText et_last_Name;
    EditText et_mail;
    EditText et_cpswd;
    EditText et_rpswd;
    Button btn_sign;
    Button btn_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        et_first_name = findViewById(R.id.et_first_name);
        et_last_Name = findViewById(R.id.et_last_Name);
        et_mail = findViewById(R.id.et_mail);
        et_cpswd = findViewById(R.id.et_cpswd);
        et_rpswd = findViewById(R.id.et_rpswd);
        btn_sign = findViewById(R.id.btn_sign);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    requestParams params = new requestParams();
                    params.addParameter("email", et_mail.getText().toString())
                            .addParameter("password", et_cpswd.getText().toString())
                            .addParameter("fname", et_first_name.getText().toString())
                            .addParameter("lname", et_last_Name.getText().toString());

                    new AsyncTasks(SignUp.this, params).execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup");
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    public void handleData(LoggedUser user) {
        if (user.getUser_email()==null) {
            Toast.makeText(getApplicationContext(),"Some Issue, Please Try Again",Toast.LENGTH_SHORT).show();
        }
        else{
            finish();
        }
    }
}
