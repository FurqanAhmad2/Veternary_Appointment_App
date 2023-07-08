package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class UserAppointmentStatus extends AppCompatActivity {

    DbQueries dbQueries;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_status);
        dbQueries=new DbQueries(getApplicationContext());
        status=findViewById(R.id.status);
        Func();
    }

    public void Func(){
        // Get the email address from your Singleton or any other source
        MySingleton mySingleton = MySingleton.getInstance();
        String emailAddress = mySingleton.getVariableValue();

        int appointmentCount = dbQueries.searchApproveAppointmentByEmail(emailAddress);

        if (appointmentCount > 0) {
            // Appointments found for the given email
            Log.d("TAG", "Appointments Approved: " + appointmentCount);
            Toast.makeText(this,"Approved",Toast.LENGTH_SHORT).show();
            status.setText("Your Appointment Has been Approved");


        }
        else{

            appointmentCount = dbQueries.searchdeclineAppointmentByEmail(emailAddress);

            if (appointmentCount > 0) {
                // Appointments found for the given email
                Log.d("TAG", "Appointments Declined: " + appointmentCount);
                Toast.makeText(this,"Declined",Toast.LENGTH_SHORT).show();
                status.setText("Your Appointment Has been Declined");
            }
            else{
                Toast.makeText(this,"Pending",Toast.LENGTH_SHORT).show();
            }
        }



    }
}