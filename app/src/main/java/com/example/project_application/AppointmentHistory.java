package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentHistory extends AppCompatActivity {

    DbQueries dbQueries;
    private String[] selectedAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        dbQueries = new DbQueries(getApplicationContext());

        // Get a reference to the LinearLayout inside the ScrollView
        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        MySingleton mySingleton = MySingleton.getInstance();
        String emailAddress = mySingleton.getVariableValue();
        // Call the getAllAppointments() function to retrieve the appointment data
        ArrayList<HashMap<String, String>> appointments = new ArrayList<>();
        appointments.addAll(dbQueries.getAllAppointmentsPending(emailAddress));
        appointments.addAll(dbQueries.getAllAppointmentsDeclined(emailAddress));
        appointments.addAll(dbQueries.getAllAppointmentsApproved(emailAddress));

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

            }

            // Add the appointment layout to the main linear layout
            linearLayout.addView(appointmentLayout);
        }
    }
}