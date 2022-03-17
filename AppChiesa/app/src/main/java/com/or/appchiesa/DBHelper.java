package com.or.appchiesa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    // Define variables for database
    public static final String DATABASE_NAME = "Church.db";
    public static final String LIGHTS_TABLE_NAME = "LIGHTS";
    public static final String SCENARIOS_TABLE_NAME = "SCENARIOS";
    public static final String SECTIONS_TABLE_NAME = "SECTIONS";
    public static final String UTILS_TABLE_NAME = "UTILS";
    public static final String LIGHTS_COLUMN_ID = "_id";
    public static final String LIGHTS_COLUMN_NAME = "NAME";
    public static final String LIGHTS_COLUMN_OPNAME = "OPNAME";
    public static final String LIGHTS_COLUMN_SECTION = "SECTION";
    public static final String LIGHTS_COLUMN_STATE = "STATE";
    public static final String LIGHTS_COLUMN_SCENARIO = "SCENARIO";
    public static final String SCENARIOS_COLUMN_STATE = "STATE";
    public static final String SCENARIOS_COLUMN_NAME = "NAME";
    public static final String SCENARIOS_COLUMN_LIGHTS = "LIGHTS";
    public static final String SECTIONS_COLUMN_NAME = "NAME";
    public static final String UTILS_COLUMN_PASSWORD = "PASSWORD";
    public static final String UTILS_COLUMN_IP_ADDRESS = "IP_ADDRESS";

    public static ArrayList<String> sectionsArray = new ArrayList<>(Arrays.asList(
            "Cappelle",
            "Navata",
            "Chiesa",
            "Presbiterio",
            "Coro"
    ));

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
                "SECTION TEXT," +
                "STATE INTEGER);");

        db.execSQL("CREATE TABLE SCENARIOS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "LIGHTS TEXT, " +
                "STATE INTEGER);");

        db.execSQL("CREATE TABLE SECTIONS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT);");

        db.execSQL("CREATE TABLE UTILS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "IP_ADDRESS TEXT, " +
                "PASSWORD TEXT);");

        for (int i = 0; i < Light.lights.size(); i++) {
            insertLight(db,
                    Light.lights.get(i).getName(),
                    Light.lights.get(i).getOpName(),
                    Light.lights.get(i).getSection(),
                    Light.lights.get(i).getState());
        }

        for (int i = 0; i < Scenario.scenariosArray.size(); i++) {
            String[] lights = new String[Scenario.scenariosArray.get(i).getLightsOpName().size()];
            for (int j = 0; j < Scenario.scenariosArray.get(i).getLightsOpName().size(); j++) {
                lights[j] = Scenario.scenariosArray.get(i).getLightsOpName().get(j);
            }
            insertScenario(db,
                    Scenario.scenariosArray.get(i).getName(),
                    Scenario.scenariosArray.get(i).getState(),
                    lights);
        }

        for (int i = 0; i < sectionsArray.size(); i++) {
            insertSection(db, sectionsArray.get(i));
        }

        insertUtils(db, Utils.IP_ADDRESS, Utils.PASSWORD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertLight(SQLiteDatabase db, String name, String opName, String section, boolean state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_NAME, name);
        contentValues.put(LIGHTS_COLUMN_OPNAME, opName);
        contentValues.put(LIGHTS_COLUMN_STATE, state);
        contentValues.put(LIGHTS_COLUMN_SECTION, section);
        db.insert(LIGHTS_TABLE_NAME, null, contentValues);
    }

    public void deleteLight(SQLiteDatabase db, String name) {
        db.delete(LIGHTS_TABLE_NAME, LIGHTS_COLUMN_NAME + " = ?", new String[]{name});
    }

    public void insertScenario(SQLiteDatabase db, String name, boolean state, String[] lights) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCENARIOS_COLUMN_STATE, state);
        contentValues.put(SCENARIOS_COLUMN_NAME, name);
        String lightsStr = convertArrayToString(lights);
        contentValues.put(SCENARIOS_COLUMN_LIGHTS, lightsStr);
        db.insert(SCENARIOS_TABLE_NAME, null, contentValues);
    }

    public void deleteScenario(SQLiteDatabase db, String name) {
        db.delete(SCENARIOS_TABLE_NAME, SCENARIOS_COLUMN_NAME + " = ?", new String[]{name});
    }

    public void insertSection(SQLiteDatabase db, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECTIONS_COLUMN_NAME, name);
        db.insert(SECTIONS_TABLE_NAME, null, contentValues);
    }

    public void updateLightState(boolean state, String name, String opName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_STATE, !state);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ? AND OPNAME = ?",
                new String[]{name, opName});
    }

    public void updateLightState(boolean state, String opName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_STATE, !state);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "OPNAME = ?",
                new String[]{opName});
    }

    public void insertUtils(SQLiteDatabase db, String ipAddress, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTILS_COLUMN_PASSWORD, password);
        contentValues.put(UTILS_COLUMN_IP_ADDRESS, ipAddress);
        db.insert(UTILS_TABLE_NAME, null, contentValues);
    }

    public void updatePassword(String newPassword, String oldPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTILS_COLUMN_PASSWORD, newPassword);
        db.update(UTILS_TABLE_NAME,
                contentValues,
                "PASSWORD = ?",
                new String[]{oldPassword});
    }

    public void updateIpAddress(String newIpAddress, String oldIpAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTILS_COLUMN_IP_ADDRESS, newIpAddress);
        db.update(UTILS_TABLE_NAME,
                contentValues,
                "IP_ADDRESS = ?",
                new String[]{oldIpAddress});
    }

    public String getPassword() {
        String password = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UTILS_TABLE_NAME, new String[]{UTILS_COLUMN_PASSWORD},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            password = cursor.getString(cursor.getColumnIndex(UTILS_COLUMN_PASSWORD));
            cursor.moveToNext();
        }
        cursor.close();

        return password;
    }

    public String getIpAddress() {
        String ipAddress = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UTILS_TABLE_NAME, new String[]{UTILS_COLUMN_IP_ADDRESS},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ipAddress = cursor.getString(cursor.getColumnIndex(UTILS_COLUMN_IP_ADDRESS));
            cursor.moveToNext();
        }
        cursor.close();

        return ipAddress;
    }

    public void updateLightName(String oldName, String newName, String opName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_NAME, newName);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ? AND OPNAME = ?",
                new String[]{oldName, opName});
    }

    public void updateLightOpName(String name, String opName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_OPNAME, opName);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{name});
    }


    public void updateLightSection(String lightName, String section) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIGHTS_COLUMN_SECTION, section);
        db.update(LIGHTS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{lightName});
    }

    public void updateScenarioName(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCENARIOS_COLUMN_NAME, newName);
        db.update(SCENARIOS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{oldName});
    }

    public void updateScenarioLights(String name, String[] lights) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String lightsStr = convertArrayToString(lights);
        contentValues.put(SCENARIOS_COLUMN_LIGHTS, lightsStr);
        db.update(SCENARIOS_TABLE_NAME,
                contentValues,
                "NAME = ?",
                new String[]{name});
    }

    public ArrayList<String> getAllSectionsName() {
        ArrayList<String> sectionsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SECTIONS_TABLE_NAME, new String[]{SECTIONS_COLUMN_NAME},
                null,
                null,
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            sectionsName.add(cursor.getString(cursor.getColumnIndex(SECTIONS_COLUMN_NAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return sectionsName;
    }


    public ArrayList<String> getAllLightsNameFromSection(String section) {
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

    public ArrayList<String> getAllLightsNameFromSection() {
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
            if (cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
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
            if (cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
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

    public Boolean getLightState(String opName) {
        Boolean lightState = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_STATE},
                "OPNAME = ?",
                new String[]{opName},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Boolean state;
            if (cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
                state = false;
            } else {
                state = true;
            }
            lightState = state;
            cursor.moveToNext();
        }
        cursor.close();

        return lightState;
    }

    public ArrayList<String> getAllLightsOpNameFromSection(String section) {
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

    public ArrayList<String> getAllLightsOpNameFromSection() {
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

    public String getLightOpNameFromName(String name) {
        String opName = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LIGHTS", new String[]{LIGHTS_COLUMN_NAME},
                "NAME = ?",
                new String[]{name},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            opName = cursor.getString(cursor.getColumnIndex(LIGHTS_COLUMN_NAME));
            cursor.moveToNext();
        }
        cursor.close();

        return opName;
    }

    // SCENARIOS TABLE
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

    public ArrayList<String> getScenariosExceptOne(String exception) {
        ArrayList<String> scenariosName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_NAME},
                "NAME != ?",
                new String[]{exception},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            scenariosName.add(cursor.getString(cursor.getColumnIndex(SCENARIOS_COLUMN_NAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return scenariosName;
    }

    public String getAllScenarioLights(String scenario) {
        String scenariosLights = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_LIGHTS},
                "NAME = ?",
                new String[]{scenario},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            scenariosLights = cursor.getString(cursor.getColumnIndex(SCENARIOS_COLUMN_LIGHTS));
            cursor.moveToNext();
        }
        cursor.close();

        return scenariosLights;
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
            if (cursor.getInt(cursor.getColumnIndex(SCENARIOS_COLUMN_STATE)) == 0) {
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

    public ArrayList<Boolean> getAllScenariosStateExceptOne(String exception) {
        ArrayList<Boolean> scenariosState = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_STATE},
                "NAME != ?",
                new String[]{exception},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Boolean state;
            if (cursor.getInt(cursor.getColumnIndex(SCENARIOS_COLUMN_STATE)) == 0) {
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

    public Boolean getScenarioState(String scenario) {
        Boolean scenarioState = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_STATE},
                "NAME = ?",
                new String[]{scenario},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getInt(cursor.getColumnIndex(SCENARIOS_COLUMN_STATE)) == 0) {
                scenarioState = false;
            } else {
                scenarioState = true;
            }
            cursor.moveToNext();
        }
        cursor.close();

        return scenarioState;
    }

    public ArrayList<String> getLightsOpNameFromScenario(String scenario) {
        ArrayList<String> lightsName = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENARIOS_TABLE_NAME, new String[]{SCENARIOS_COLUMN_LIGHTS},
                "NAME = ?",
                new String[]{scenario},
                null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lightsName.add(cursor.getString(cursor.getColumnIndex(SCENARIOS_COLUMN_LIGHTS)));
            cursor.moveToNext();
        }
        cursor.close();

        return lightsName;
    }

    public void updateScenarioState(boolean state, String name) {
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
            if (cursor.getInt(cursor.getColumnIndex(LIGHTS_COLUMN_STATE)) == 0) {
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

    public static String convertArrayToString(String[] array) {
        String strSeparator = "__,__";
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }
}
