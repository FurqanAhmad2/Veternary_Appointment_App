package com.example.project_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

public class AddDoctor extends AppCompatActivity {

    DbQueries dbQueries;
    EditText Name, Age, phoneNumber, Degree, Fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbQueries=new DbQueries(getApplicationContext());
        setContentView(R.layout.activity_add_doctor);
        Name=findViewById(R.id.name_input);
        Age=findViewById(R.id.age);
        phoneNumber=findViewById(R.id.pNumber);
        Degree=findViewById(R.id.degree);
        Fee=findViewById(R.id.fee);

    }

    public void SaveIntoDB(View view) {
        HashMap<String,String> contact= new HashMap<String,String>();
        contact.put("Name",Name.getText().toString());
        contact.put("Age",Age.getText().toString());
        contact.put("phoneNumber",phoneNumber.getText().toString());
        contact.put("degree",Degree.getText().toString());
        contact.put("fee",Fee.getText().toString());
        dbQueries.InsertSingleContact(contact);

        Toast.makeText(this, "Doctor Added", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }


}