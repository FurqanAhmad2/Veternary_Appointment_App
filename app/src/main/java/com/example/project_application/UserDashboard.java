package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

    public void LogOutApp(View view) {
        Intent intent = new Intent(this, UserPageMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back on pressing back button
    }

    public void add_Appoin(View view) {
        Intent intent=new Intent(this,AddAppointmentActivity.class);
        startActivity(intent);
    }

    public void CheckStatus(View view) {

        Intent intent=new Intent(this,UserAppointmentStatus.class);
        startActivity(intent);
    }

    public void historyAppoint(View view) {
        Intent intent=new Intent(this,AppointmentHistory.class);
        startActivity(intent);
    }
}