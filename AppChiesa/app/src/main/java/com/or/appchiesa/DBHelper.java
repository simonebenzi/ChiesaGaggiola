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
    public static final String SCENARIOS_TABLE_NAME = "SCENARIOS";
    public static final String LIGHTS_COLUMN_ID = "_id";
    public static final String LIGHTS_COLUMN_NAME = "NAME";
    public static final String LIGHTS_COLUMN_OPNAME = "OPNAME";
    public static final String LIGHTS_COLUMN_IP_ADDRESS = "IP_ADDRESS";
    public static final String LIGHTS_COLUMN_SECTION = "SECTION";
    public static final String LIGHTS_COLUMN_STATE = "STATE";
    public static final String LIGHTS_COLUMN_SCENARIO = "SCENARIO";
    public static final String SCENARIOS_COLUMN_STATE = "STATE";
    public static final String SCENARIOS_COLUMN_NAME = "NAME";

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
                "SECTION TEXT," +
                "SCENARIO TEXT, " +
                "STATE INTEGER);");

        db.execSQL("CREATE TABLE SCENARIOS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "STATE INTEGER);");

        for(int i = 0; i < Light.lights.size(); i++) {
            insertLight(db,
                    Light.lights.get(i).getName(),
                    Light.lights.get(i).getOpName(),
                    Light.lights.get(i).getIpAddress(),
                    Light.lights.get(i).getSection(),
                    Light.lights.get(i).getScenario(),
                    Light.lights.get(i).getState());
        }

        for(int i = 0; i < Scenario.scenariosArray.size(); i++) {
            insertScenario(db,
                    Scenario.scenariosArray.get(i).getName(),
                    Scenario.scenariosArray.get(i).getState());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertLight(SQLiteDatabase db, String name, String opName, String ipAddress, String section, String scenario, boolean state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_NAME, name);
        contentValues.put(LIGHTS_COLUMN_OPNAME, opName);
        contentValues.put(LIGHTS_COLUMN_IP_ADDRESS, ipAddress);
        contentValues.put(LIGHTS_COLUMN_STATE, state);
        contentValues.put(LIGHTS_COLUMN_SECTION, section);
        contentValues.put(LIGHTS_COLUMN_SCENARIO, scenario);
        db.insert(LIGHTS_TABLE_NAME, null, contentValues);
    }

    public void insertScenario(SQLiteDatabase db, String name, boolean state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCENARIOS_COLUMN_STATE, state);
        contentValues.put(SCENARIOS_COLUMN_NAME, name);
        db.insert(SCENARIOS_TABLE_NAME, null, contentValues);
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

    public void updateScenarioName(String oldName, String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCENARIOS_COLUMN_NAME, newName);
        db.update(SCENARIOS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{oldName});
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

    public ArrayList<String> getScenarioName(String name) {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_NAME},
                "SECTION = ?",
                new String[]{name},
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

    public ArrayList<Boolean> getAllLightsState() {
        ArrayList<Boolean> lightsState = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_STATE},
                null,
                null,
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

    public ArrayList<String> getAllLightsIpAddress() {
        ArrayList<String> lightsIpAddress = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_IP_ADDRESS},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsIpAddress.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_IP_ADDRESS)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsIpAddress;
    }

    public ArrayList<String> getAllScenariosName() {
        ArrayList<String> scenariosName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_NAME},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            scenariosName.add(cursor.getString(cursor.getColumnIndex(SCENARIOS_COLUMN_NAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return scenariosName;
    }

    public ArrayList<Boolean> getAllScenariosState() {
        ArrayList<Boolean> scenariosState = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_STATE},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Boolean state;
            if(cursor.getInt(cursor.getColumnIndex(SCENARIOS_COLUMN_STATE)) == 0) {
                state = false;
            } else {
                state = true;
            }
            scenariosState.add(state);
            cursor.moveToNext();
        }
        cursor.close();

        return scenariosState;
    }

    public ArrayList<String> getLightsNameFromScenario(String scenario) {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_NAME},
                "SCENARIO = ?",
                new String[]{scenario},
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

    public ArrayList<String> getIpAddressFromScenario(String scenario) {
        ArrayList<String> lightsIpAddress = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_IP_ADDRESS},
                "SCENARIO = ?",
                new String[]{scenario},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsIpAddress.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_IP_ADDRESS)));
            Log.e("PROVA", lightsIpAddress.get(0));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsIpAddress;
    }

    public void updateScenarioState(boolean state, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCENARIOS_COLUMN_STATE, !state);
        db.update(SCENARIOS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{name});
    }

    public ArrayList<String> getLightsNameFromSection(String section) {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_NAME},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsName.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_NAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsName;
    }

    public ArrayList<String> getLightsOpNameFromSection(String section) {
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

    public ArrayList<String> getLightsIpAddressFromSection(String section) {
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

    public ArrayList<String> getLightsScenarioFromSection(String section) {
        ArrayList<String> lightsScenario = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_SCENARIO},
                "SECTION = ?",
                new String[]{section},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsScenario.add(cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_SCENARIO)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsScenario;
    }

    public ArrayList<Boolean> getLightsStateFromSection(String section) {
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
}
