package com.or.appchiesa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "appchiesa";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateState(SQLiteDatabase db, int id, ContentValues stateValue) {
        db.update("LIGHTS",
                stateValue,
                "_id = ?",
                new String[]{Integer.toString(id)});
    }

    private static void insertLight(SQLiteDatabase db, String name,
                                    String opName, String ipAddress,
                                    String section, int imageResourceId,
                                    Boolean state) {
        ContentValues lightValues = new ContentValues();
        lightValues.put("NAME", name);
        lightValues.put("OP_NAME", opName);
        lightValues.put("IMAGE_RESOURCE_ID", imageResourceId);
        lightValues.put("IP_ADDRESS", ipAddress);
        lightValues.put("SECTION", section);
        lightValues.put("STATE", state);
        db.insert("LIGHTS", null, lightValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE LIGHTS (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, " +
                    "OP_NAME TEXT, " +
                    "IP_ADDRESS TEXT, " +
                    "SECTION TEXT, " +
                    "STATE INTEGER, " +
                    "IMAGE_RESOURCE_ID INTEGER);");

            for(Light light:Light.lights){
                insertLight(db, light.getName(), light.getOpName(),
                        light.getIpAddress(), light.getSection(), light.getImageResourceId(),
                        light.getState());
            }

        }
    }
}
