package com.example.project_application;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbQueries extends SQLiteOpenHelper {

    public DbQueries(Context context){
        super(context,"ContactsDB",null,1);
        Log.d("TAG","Database Created...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SqlTableQuery="CREATE TABLE DOCTORS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Name TEXT,"+
                "Age TEXT,"+
                "degree TEXT,"+
                "phoneNumber TEXT,"+
                "fee TEXT)";

        String SqlTableQuery_01="CREATE TABLE USERS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Name TEXT,"+
                "Age TEXT,"+
                "Email TEXT,"+
                "phoneNumber TEXT,"+
                "Password TEXT)";


        String SqlTableQuery_02="CREATE TABLE APPOINTMENT("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Email TEXT,"+
                "DoctorName TEXT,"+
                "Date TEXT,"+
                "Time TEXT,"+
                "Status TEXT)";

        String SqlTableQuery_03="CREATE TABLE APPROVEDAPPOINTMENT("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Email TEXT,"+
                "DoctorName TEXT,"+
                "Date TEXT,"+
                "Time TEXT,"+
                "Status TEXT)";

        String SqlTableQuery_04="CREATE TABLE DECLINEDAPPOINTMENT("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Email TEXT,"+
                "DoctorName TEXT,"+
                "Date TEXT,"+
                "Time TEXT,"+
                "Status TEXT)";

        db.execSQL(SqlTableQuery);
        db.execSQL(SqlTableQuery_01);
        db.execSQL(SqlTableQuery_02);
        db.execSQL(SqlTableQuery_03);
        db.execSQL(SqlTableQuery_04);

        Log.d("TAG","Table Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void InsertSingleContact(HashMap<String, String> contact) {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("_id",contact.get("_id"));
        contentValues.put("Name",contact.get("Name"));
        contentValues.put("Age",contact.get("Age"));
        contentValues.put("degree",contact.get("degree"));
        contentValues.put("phoneNumber",contact.get("phoneNumber"));
        contentValues.put("fee",contact.get("fee"));


        int result= (int) db.insert("DOCTORS",null,contentValues);

        if(result!=-1){

            Log.d("TAG","New Doctor Inserted with ID"+result);


        }else{
            Log.d("TAG","New Doctor Insertion failed with ID"+result);
        }




        db.close();
    }
    public void InsertSingleUser(HashMap<String, String> contact) {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("_id",contact.get("_id"));
        contentValues.put("Name", contact.get("Name"));
        contentValues.put("Age", contact.get("Age"));
        contentValues.put("Email", contact.get("Email")); // Previously mapped to "degree"
        contentValues.put("phoneNumber", contact.get("phoneNumber"));
        contentValues.put("Password", contact.get("Password"));



        int result= (int) db.insert("USERS",null,contentValues);

        if(result!=-1){

            Log.d("TAG","New User Inserted with ID"+result);


        }else{
            Log.d("TAG","New User Insertion failed with ID"+result);
        }




        db.close();
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{id};
        int result = db.delete("CONTACTS", whereClause, whereArgs);
        if (result > 0) {
            Log.d("TAG", "Contact with id " + id + " deleted from database");
        } else {
            Log.d("TAG", "Deletion failed for contact with id " + id);
        }
        db.close();
    }

    public void updateContactdb(String id, @NonNull HashMap<String, String> contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", contact.get("firstName"));
        contentValues.put("lastName", contact.get("lastName"));
        contentValues.put("phoneNumber", contact.get("phoneNumber"));
        contentValues.put("emailAddress", contact.get("emailAddress"));
        contentValues.put("homeAddress", contact.get("homeAddress"));

        String whereClause = "_id=?";
        String[] whereArgs = new String[]{id};

        int result = db.update("CONTACTS", contentValues, whereClause, whereArgs);
        if (result > 0) {
            Log.d("TAG", "Contact with id " + id + " updated in the database");
        } else {
            Log.d("TAG", "Update failed for contact with id " + id);
        }
        db.close();
    }




    public ArrayList<HashMap<String,String>> getAllContacts(){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<HashMap<String,String>> contactList=new ArrayList<HashMap<String,String>>();

        String Query="SELECT * FROM CONTACTS";
        Cursor cursor=db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            do{
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("_id",cursor.getString(0));
                hashMap.put("firstName", cursor.getString(1));
                hashMap.put("lastName", cursor.getString(2));
                hashMap.put("phoneNumber", cursor.getString(3));
                hashMap.put("emailAddress", cursor.getString(4));
                hashMap.put("homeAddress", cursor.getString(5));


                contactList.add(hashMap);
            }while (cursor.moveToNext());
        }

        return contactList;
    }

    public HashMap<String,String> getSingleContact(String email,String pass) {
        SQLiteDatabase db=getReadableDatabase();
        HashMap<String,String> hashMap= new HashMap<String,String>();
        String Query = "SELECT * FROM USERS WHERE Email='" + email + "' AND Password='" + pass + "'";
        Cursor cursor=db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            hashMap.put("_id",cursor.getString(0));
            hashMap.put("Name", cursor.getString(1));
            hashMap.put("Age", cursor.getString(2));
            hashMap.put("Email", cursor.getString(3));
            hashMap.put("phoneNumber", cursor.getString(4));
            hashMap.put("Password", cursor.getString(5));

        }

        return hashMap;
    }


    public String[] getAllDoctorNames() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                "DOCTORS", // Replace with the actual table name
                new String[]{"Name"}, // Specify the column(s) you want to retrieve
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> doctorNamesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String doctorName = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                doctorNamesList.add(doctorName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return doctorNamesList.toArray(new String[doctorNamesList.size()]);
    }


    public void InsertSingleAppointment(HashMap<String, String> appointment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", appointment.get("_id"));
        contentValues.put("Email", appointment.get("Email"));
        contentValues.put("DoctorName", appointment.get("DoctorName"));
        contentValues.put("Date", appointment.get("Date"));
        contentValues.put("Time", appointment.get("Time"));
        contentValues.put("Status", appointment.get("Status"));

        long result = db.insert("APPOINTMENT", null, contentValues);

        if (result != -1) {
            Log.d("TAG", "New Appointment Inserted with ID: " + result);
        } else {
            Log.d("TAG", "New Appointment Insertion failed with ID: " + result);
        }

        db.close();
    }

    public void InsertSingleAppointmentApproved(HashMap<String, String> appointment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", appointment.get("_id"));
        contentValues.put("Email", appointment.get("Email"));
        contentValues.put("DoctorName", appointment.get("DoctorName"));
        contentValues.put("Date", appointment.get("Date"));
        contentValues.put("Time", appointment.get("Time"));
        contentValues.put("Status", appointment.get("Status"));

        long result = db.insert("APPROVEDAPPOINTMENT", null, contentValues);

        if (result != -1) {
            Log.d("TAG", "New Approved Appointment Inserted with ID: " + result);
        } else {
            Log.d("TAG", "New Approved Appointment Insertion failed with ID: " + result);
        }

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllAppointments() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM APPOINTMENT";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("Email", cursor.getString(cursor.getColumnIndex("Email")));
                hashMap.put("DoctorName", cursor.getString(cursor.getColumnIndex("DoctorName")));
                hashMap.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
                hashMap.put("Time", cursor.getString(cursor.getColumnIndex("Time")));

                appointmentList.add(hashMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return appointmentList;
    }

    public void removeAppointmentByEmail(String email) {
        SQLiteDatabase db = getWritableDatabase();

        // Define the WHERE clause with the email
        String whereClause = "Email = ?";
        String[] whereArgs = { email };

        // Delete the appointment(s) matching the email
        db.delete("APPOINTMENT", whereClause, whereArgs);

        db.close();
    }

    public void InsertSingleAppointmentDeclined(HashMap<String, String> appointment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", appointment.get("_id"));
        contentValues.put("Email", appointment.get("Email"));
        contentValues.put("DoctorName", appointment.get("DoctorName"));
        contentValues.put("Date", appointment.get("Date"));
        contentValues.put("Time", appointment.get("Time"));
        contentValues.put("Status", appointment.get("Status"));

        long result = db.insert("DECLINEDAPPOINTMENT", null, contentValues);


        if (result != -1) {
            Log.d("TAG", "New Declined Appointment Inserted with ID: " + result);
        } else {
            Log.d("TAG", "New Declined Appointment Insertion failed with ID: " + result);
        }

        db.close();
    }



    public int searchdeclineAppointmentByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"_id"};
        String selection = "Email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("DECLINEDAPPOINTMENT", projection, selection, selectionArgs, null, null, null);

        int resultCount = cursor.getCount();

        cursor.close();
        db.close();

        return resultCount;
    }


    public int searchApproveAppointmentByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"_id"};
        String selection = "Email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("APPROVEDAPPOINTMENT", projection, selection, selectionArgs, null, null, null);

        int resultCount = cursor.getCount();

        cursor.close();
        db.close();

        return resultCount;
    }


    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllAppointmentsPending(String email) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();

        String[] projection = {"Email", "DoctorName", "Date", "Time"};
        String selection = "Email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("APPOINTMENT", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Email", cursor.getString(cursor.getColumnIndex("Email")));
                hashMap.put("DoctorName", cursor.getString(cursor.getColumnIndex("DoctorName")));
                hashMap.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
                hashMap.put("Time", cursor.getString(cursor.getColumnIndex("Time")));

                appointmentList.add(hashMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return appointmentList;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllAppointmentsApproved(String email) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();

        String[] projection = {"Email", "DoctorName", "Date", "Time"};
        String selection = "Email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("APPROVEDAPPOINTMENT", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Email", cursor.getString(cursor.getColumnIndex("Email")));
                hashMap.put("DoctorName", cursor.getString(cursor.getColumnIndex("DoctorName")));
                hashMap.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
                hashMap.put("Time", cursor.getString(cursor.getColumnIndex("Time")));

                appointmentList.add(hashMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return appointmentList;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllAppointmentsDeclined(String email) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();

        String[] projection = {"Email", "DoctorName", "Date", "Time"};
        String selection = "Email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query("DECLINEDAPPOINTMENT", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Email", cursor.getString(cursor.getColumnIndex("Email")));
                hashMap.put("DoctorName", cursor.getString(cursor.getColumnIndex("DoctorName")));
                hashMap.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
                hashMap.put("Time", cursor.getString(cursor.getColumnIndex("Time")));

                appointmentList.add(hashMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return appointmentList;
    }







}
