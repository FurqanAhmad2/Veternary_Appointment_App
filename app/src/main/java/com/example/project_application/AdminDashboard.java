package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void add_Doctor(View view) {
        Intent intent= new Intent(this,AddDoctor.class);
        startActivity(intent);
    }

    public void Approve_app(View view) {
        Intent intent= new Intent(this,AdminApproveAppoint.class);
        startActivity(intent);
    }


    public void LogOutApp(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back on pressing back button
    }
}