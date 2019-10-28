package com.example.midterm_2018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Inbox extends AppCompatActivity implements getAllMails.message {

    TextView tv_name;
    ImageView iv_create;
    ImageView iv_logout;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    ArrayList<email> emailArrayList;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Inbox");

        tv_name = findViewById(R.id.tv_name);

        iv_create = findViewById(R.id.iv_create);
        iv_logout = findViewById(R.id.iv_logout);

        String name = getIntent().getExtras().getString("name");
        tv_name.setText(name);
        SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        token = prefs.getString("token", "123");

        new getAllMails(Inbox.this, token).execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox");

        iv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inbox.this, CreateNewMail.class);
                startActivity(i);
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void handleMessage(ArrayList<Messages> allmessage) {
        mrecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new emailAdapter(allmessage,token);
        mrecyclerView.setAdapter(mAdapter);
    }
}
