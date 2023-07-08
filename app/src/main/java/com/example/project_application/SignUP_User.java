package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignUP_User extends AppCompatActivity {

    DbQueries dbQueries;
    EditText Name,Phone,Email,Pass,Age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);


        dbQueries=new DbQueries(getApplicationContext());
        Name=findViewById(R.id.name_input);
        Phone=findViewById(R.id.pNumber);
        Email=findViewById(R.id.Email);
        Pass=findViewById(R.id.Pass);
        Age=findViewById(R.id.age);


    }

    public void SaveIntoDB(View view) {
        HashMap<String,String> contact= new HashMap<String,String>();
        contact.put("Name",Name.getText().toString());
        contact.put("Age",Age.getText().toString());
        contact.put("phoneNumber",Phone.getText().toString());
        contact.put("Email",Email.getText().toString());
        contact.put("Password",Pass.getText().toString());
        dbQueries.InsertSingleUser(contact);

        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,UserPageMain.class);
        startActivity(intent);

    }
}