package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_Login extends AppCompatActivity {

    EditText Email,Pass;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Email=findViewById(R.id.admin_email_input);
        Pass=findViewById(R.id.admin_password_input);
    }

    public void Login(View view) {
        String textEmail = Email.getText().toString();
        String textPass = Pass.getText().toString();

        if (textEmail.equals("Admin") && textPass.equals("123")) {
            Intent intent = new Intent(this, AdminDashboard.class);

            startActivity(intent);
        } else {
            Toast.makeText(this, "Wrong credentials. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

}