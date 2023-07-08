package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserPageMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page_main);
    }

    public void LoginUser(View view) {
        Intent intent=new Intent(this,LoginUser.class);
        startActivity(intent);
    }

    public void SignUP_User(View view) {
        Intent intent=new Intent(this,SignUP_User.class);
        startActivity(intent);
    }
}