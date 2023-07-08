package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginUser extends AppCompatActivity {

    EditText Email,Pass;
    DbQueries dbQueries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        dbQueries=new DbQueries(getApplicationContext());
        Email=findViewById(R.id.user_email_input);
        Pass=findViewById(R.id.user_password_input);

    }

    public void Login(View view) {
        String email = Email.getText().toString();
        String password = Pass.getText().toString();

        HashMap<String,String> hashMap_01= new HashMap<String,String>();

        // Check if email and password are not empty
        if (!email.isEmpty() && !password.isEmpty()) {
            hashMap_01=dbQueries.getSingleContact(email,password);
            MySingleton mySingleton = MySingleton.getInstance();
            mySingleton.setVariableValue(email);
            Toast.makeText(this,"Logging IN",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,UserDashboard.class);
            startActivity(intent);

        } else {
            // Email or password is empty
            // You can display an error message or take appropriate action
            Toast.makeText(this,"Invalid Credentails",Toast.LENGTH_SHORT).show();
        }
    }

}