package com.or.appchiesa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    // Define variables for database
    public static final String DATABASE_NAME = "Church.db";
    public static final String LIGHTS_TABLE_NAME = "LIGHTS";
    public static final String LIGHTS_COLUMN_ID = "_id";
    public static final String LIGHTS_COLUMN_NAME = "NAME";
    public static final String LIGHTS_COLUMN_OPNAME = "OPNAME";
    public static final String LIGHTS_COLUMN_IP_ADDRESS = "IP_ADDRESS";
    public static final String LIGHTS_COLUMN_SECTION = "SECTION";
    public static final String LIGHTS_COLUMN_STATE = "STATE";

    private HashMap hashMap;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LIGHTS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "OPNAME TEXT, " +
                "IP_ADDRESS TEXT, " +
                "SECTION TEXT, " +
                "STATE INTEGER);");

        for( int i = 0; i < Light.lights.size(); i++) {
            insertLight(db,
                    Light.lights.get(i).getName(),
                    Light.lights.get(i).getOpName(),
                    Light.lights.get(i).getIpAddress(),
                    Light.lights.get(i).getSection(),
                    Light.lights.get(i).getState());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertLight(SQLiteDatabase db, String name, String opName, String ipAddress, String section, boolean state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_NAME, name);
        contentValues.put(LIGHTS_COLUMN_OPNAME, opName);
        contentValues.put(LIGHTS_COLUMN_IP_ADDRESS, ipAddress);
        contentValues.put(LIGHTS_COLUMN_STATE, state);
        contentValues.put(LIGHTS_COLUMN_SECTION, section);
        db.insert(LIGHTS_TABLE_NAME, null, contentValues);
    }

    public void updateLightState(boolean state, String name, String opName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_STATE, !state);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ? AND OPNAME = ?",
                new String[]{name, opName});
    }

    public void updateLightName(String oldName, String newName, String opName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_NAME, newName);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ? AND OPNAME = ?",
                new String[]{oldName, opName});
    }

    public void updateLightIpAddress(String name, String newIpAddress, String opName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_IP_ADDRESS, newIpAddress);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ? AND OPNAME = ?",
                new String[]{name, opName});
    }

    public ArrayList<String> getAllLightsName(String section) {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_NAME},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsName.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_NAME)));
            Log.e("PROVA", lightsName.get(0));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsName;
    }

    public ArrayList<String> getAllLightsName() {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_NAME},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsName.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_NAME)));
            Log.e("PROVA", lightsName.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return lightsName;
    }

    public ArrayList<Boolean> getAllLightsState(String section) {
        ArrayList<Boolean> lightsState = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_STATE},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Boolean state;
            if(cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
                state = false;
            } else {
                state = true;
            }
            lightsState.add(state);
            cursor.moveToNext();
        }
        cursor.close();

        return lightsState;
    }

    public ArrayList<Boolean> getSectionStates(String section) {
        ArrayList<Boolean> lightsState = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_STATE},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Boolean state;
            if(cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
                state = false;
            } else {
                state = true;
            }
            lightsState.add(state);
            cursor.moveToNext();
        }
        cursor.close();

        return lightsState;
    }

    public ArrayList<String> getAllLightsOpName(String section) {
        ArrayList<String> lightsOpName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_OPNAME},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsOpName.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_OPNAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsOpName;
    }

    public ArrayList<String> getAllLightsOpName() {
        ArrayList<String> lightsOpName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_OPNAME},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsOpName.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_OPNAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsOpName;
    }

    public ArrayList<String> getAllLightsIpAddress(String section) {
        ArrayList<String> lightsIpAddress = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_IP_ADDRESS},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsIpAddress.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_IP_ADDRESS)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsIpAddress;
    }
}
