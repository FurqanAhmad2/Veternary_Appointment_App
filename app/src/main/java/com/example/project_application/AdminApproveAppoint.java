package com.example.project_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminApproveAppoint extends AppCompatActivity {

    DbQueries dbQueries;
    private String[] selectedAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_appoint);
        dbQueries = new DbQueries(getApplicationContext());

        // Get a reference to the LinearLayout inside the ScrollView
        LinearLayout linearLayout = findViewById(R.id.linear_layout);

        // Call the getAllAppointments() function to retrieve the appointment data
        ArrayList<HashMap<String, String>> appointments = dbQueries.getAllAppointments();

        // Initialize the selectedAppointment array
        selectedAppointment = new String[4]; // Assuming there are 4 attributes: Email, Doctor Name, Time, and Date

        // Iterate over the retrieved appointments
        for (HashMap<String, String> appointment : appointments) {
            // Create a new horizontal LinearLayout for each appointment
            LinearLayout appointmentLayout = new LinearLayout(this);
            appointmentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            appointmentLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextViews for each attribute and set their values
            for (String key : appointment.keySet()) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                textView.setText(appointment.get(key));
                appointmentLayout.addView(textView);

                // Set onClick listener for each TextView
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Store the selected appointment value in the array
                        selectedAppointment[0] = appointment.get("Email");
                        selectedAppointment[1] = appointment.get("DoctorName");
                        selectedAppointment[2] = appointment.get("Time");
                        selectedAppointment[3] = appointment.get("Date");

                        // Show an AlertDialog with approve/decline options
                        showApproveDeclineDialog();
                    }
                });
            }

            // Add the appointment layout to the main linear layout
            linearLayout.addView(appointmentLayout);
        }
    }

    private void showApproveDeclineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Appointment Confirmation");
        builder.setMessage("Do you want to approve or decline this appointment?");
        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle approve action
                // ...
                dbQueries.removeAppointmentByEmail(selectedAppointment[0]);
                HashMap<String, String> appointmentMap = new HashMap<>();
                appointmentMap.put("Email", selectedAppointment[0]);
                appointmentMap.put("DoctorName", selectedAppointment[1]);
                appointmentMap.put("Time", selectedAppointment[2]);
                appointmentMap.put("Date", selectedAppointment[3]);

                dbQueries.InsertSingleAppointmentApproved(appointmentMap);
                show();
            }
        });
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle decline action
                // ...

                dbQueries.removeAppointmentByEmail(selectedAppointment[0]);
                HashMap<String, String> appointmentMap = new HashMap<>();
                appointmentMap.put("Email", selectedAppointment[0]);
                appointmentMap.put("DoctorName", selectedAppointment[1]);
                appointmentMap.put("Time", selectedAppointment[2]);
                appointmentMap.put("Date", selectedAppointment[3]);
                dbQueries.InsertSingleAppointmentDeclined(appointmentMap);
                showDec();


            }
        });
        builder.create().show();
    }

    public void show(){
        Toast.makeText(this,"Appointment Approved",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }

    public void showDec(){
        Toast.makeText(this,"Appointment Declined",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }
}

