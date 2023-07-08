package com.example.project_application;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_application.R;

import java.util.Calendar;
import java.util.HashMap;

public class AddAppointmentActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private EditText dateSelector;
    private EditText timeSelector;

    private String selectedOption;
    private String selectedDate;
    private String selectedTime;
    DbQueries dbQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        dbQueries=new DbQueries(getApplicationContext());
        // Initialize views
        dropdown = findViewById(R.id.dropdown);
        dateSelector = findViewById(R.id.dateSelector);
        timeSelector = findViewById(R.id.timeSelector);

        // Create array adapter for the dropdown

        String[] doctorNames = dbQueries.getAllDoctorNames();
        // Use the doctorNames array as needed

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        // Set click listeners for date and time selectors
        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    // Spinner item selection callback
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedOption = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle situation where no option is selected
        selectedOption = "";
    }

    // Date picker dialog callback
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateSelector.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Time picker dialog callback
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        timeSelector.setText(selectedTime);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }


    public void saveAppointment(View view) {
        // Retrieve selected values
        String selectedOption = dropdown.getSelectedItem().toString();
        String selectedDate = dateSelector.getText().toString();
        String selectedTime = timeSelector.getText().toString();

        // Get the email address from your Singleton or any other source
        MySingleton mySingleton = MySingleton.getInstance();
        String emailAddress = mySingleton.getVariableValue();

        // Set the initial status as "0"
        String status = "0";

        // Perform any desired actions with the retrieved values, email address, and status

        // You can also call the InsertSingleAppointment() method to save the appointment details to the database
        HashMap<String, String> appointmentData = new HashMap<>();
        appointmentData.put("Email", emailAddress);
        appointmentData.put("DoctorName", selectedOption);
        appointmentData.put("Date", selectedDate);
        appointmentData.put("Time", selectedTime);
        appointmentData.put("Status", status);

        dbQueries.InsertSingleAppointment(appointmentData);
    }
}
