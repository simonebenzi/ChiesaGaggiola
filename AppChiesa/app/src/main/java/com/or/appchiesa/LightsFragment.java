package com.or.appchiesa;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LightsFragment extends Fragment {

    private MainRecyclerAdapter mainRecyclerAdapter;
    private Switch aSwitch;
    private DBHelper dbHelper;

    private ArrayList<Section> sectionList = new ArrayList<>();

    public MainRecyclerAdapter getMainRecyclerAdapter() {
        return this.mainRecyclerAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize sections with corresponding lights
        this.dbHelper = new DBHelper(getContext());
        initSections();

        RecyclerView mainRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_lights,
                container, false);

        // Initialize switch
        aSwitch = new Switch(getContext());

        this.mainRecyclerAdapter = new MainRecyclerAdapter(sectionList, getContext());
        this.mainRecyclerAdapter.setClickListener(new MainRecyclerAdapter.ClickListener() {
            @Override
            public void onClickLight(int position, ImageView imageView, String sectionName) {
                Drawable drawable;
                Boolean state;
                String opName, ipAddress, name;

                SQLiteOpenHelper databaseHelper = new DatabaseHelper(getContext());
                ArrayList<Boolean> lightsState = dbHelper.getAllLightsState(sectionName);
                ArrayList<String> lightsOpName = dbHelper.getAllLightsOpName(sectionName);
                ArrayList<String> lightsIpAddress = dbHelper.getAllLightsIpAddress(sectionName);
                ArrayList<String> lightsName = dbHelper.getAllLightsName(sectionName);

                state = lightsState.get(position);
                opName = lightsOpName.get(position);
                ipAddress = lightsIpAddress.get(position);
                name = lightsName.get(position);

                if(!state){
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_on);
                    imageView.setImageDrawable(drawable);
                    aSwitch.switchLightOn(ipAddress, opName);
                    dbHelper.updateLightState(state, name, opName);
                } else{
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb);
                    imageView.setImageDrawable(drawable);
                    aSwitch.switchLightOff(ipAddress, opName);
                    dbHelper.updateLightState(state, name, opName);
                }
            }

            public void onItemClick(int position, View menuImage) {
                PopupMenu popupMenu = new PopupMenu(getContext(), menuImage);
                popupMenu.inflate(R.menu.group_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modify_item:
                                displayModifyLightDialog(position);
                                //Toast.makeText(getContext(), "Rename light clicked", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.delete_item:
                                Light.lights.remove(position);
                                mainRecyclerAdapter.getAdapter().updateRecycle("light");
                                //Toast.makeText(getContext(), "Delete light clicked", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return mainRecyclerView;
    }

    public void displayModifyLightDialog(int position) {
        ModifyLightDialogFragment modifyLightDialogFragment =
                new ModifyLightDialogFragment(position);
        modifyLightDialogFragment.show(getFragmentManager(), "modify_light_dialog");
    }

    private void initSections() {
        String sectionOneName = "Navata";
        ArrayList<Light> sectionOneItems = new ArrayList<Light>();

        String sectionTwoName = "Cappelle";
        ArrayList<Light> sectionTwoItems = new ArrayList<Light>();

        String sectionThreeName = "Presbiterio";
        ArrayList<Light> sectionThreeItems = new ArrayList<Light>();

        String sectionFourName = "Coro";
        ArrayList<Light> sectionFourItems = new ArrayList<Light>();

        String sectionFiveName = "Chiesa";
        ArrayList<Light> sectionFiveItems = new ArrayList<Light>();

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(getContext());
        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            // Code to read
            // Navata section
            Cursor cursorNavata = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                            "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                    "SECTION = ?", new String[]{"Navata"},
                    null, null, null);

            if(cursorNavata.moveToFirst()) {
                for(int i = 0; i < cursorNavata.getCount(); i++){
                    String nameText = cursorNavata.getString(0);
                    String opNameText = cursorNavata.getString(1);
                    String ipAddressText = cursorNavata.getString(2);
                    String sectionText = cursorNavata.getString(3);
                    boolean state = (cursorNavata.getInt(4) == 1);
                    int resourceId = cursorNavata.getInt(5);

                    Light light = new Light(nameText, opNameText, ipAddressText, sectionText,
                            resourceId, state);
                    sectionOneItems.add(light);
                    cursorNavata.moveToNext();
                }
            }
            cursorNavata.close();
            // Cappelle section
            Cursor cursorCappelle = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                            "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                    "SECTION = ?", new String[]{"Cappelle"},
                    null, null, null);

            if(cursorCappelle.moveToFirst()) {
                for(int i = 0; i < cursorCappelle.getCount(); i++){
                    String nameText = cursorCappelle.getString(0);
                    String opNameText = cursorCappelle.getString(1);
                    String ipAddressText = cursorCappelle.getString(2);
                    String sectionText = cursorCappelle.getString(3);
                    boolean state = (cursorCappelle.getInt(4) == 1);
                    int resourceId = cursorCappelle.getInt(5);

                    Light light = new Light(nameText, opNameText, ipAddressText, sectionText,
                            resourceId, state);
                    sectionTwoItems.add(light);
                    cursorCappelle.moveToNext();
                }
            }
            cursorCappelle.close();
            // Presbiterio section
            Cursor cursorPresbiterio = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                            "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                    "SECTION = ?", new String[]{"Presbiterio"},
                    null, null, null);

            if(cursorPresbiterio.moveToFirst()) {
                for(int i = 0; i < cursorPresbiterio.getCount(); i++){
                    String nameText = cursorPresbiterio.getString(0);
                    String opNameText = cursorPresbiterio.getString(1);
                    String ipAddressText = cursorPresbiterio.getString(2);
                    String sectionText = cursorPresbiterio.getString(3);
                    boolean state = (cursorPresbiterio.getInt(4) == 1);
                    int resourceId = cursorPresbiterio.getInt(5);

                    Light light = new Light(nameText, opNameText, ipAddressText, sectionText,
                            resourceId, state);
                    sectionThreeItems.add(light);
                    cursorPresbiterio.moveToNext();
                }
            }
            cursorPresbiterio.close();
            // Coro section
            Cursor cursorCoro = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                            "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                    "SECTION = ?", new String[]{"Coro"},
                    null, null, null);

            if(cursorCoro.moveToFirst()) {
                for(int i = 0; i < cursorCoro.getCount(); i++){
                    String nameText = cursorCoro.getString(0);
                    String opNameText = cursorCoro.getString(1);
                    String ipAddressText = cursorCoro.getString(2);
                    String sectionText = cursorCoro.getString(3);
                    boolean state = (cursorCoro.getInt(4) == 1);
                    int resourceId = cursorCoro.getInt(5);

                    Light light = new Light(nameText, opNameText, ipAddressText, sectionText,
                            resourceId, state);
                    sectionFourItems.add(light);
                    cursorCoro.moveToNext();
                }
            }
            cursorCoro.close();
            // Chiesa section
            Cursor cursorChiesa = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                            "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                    "SECTION = ?", new String[]{"Chiesa"},
                    null, null, null);

            if(cursorChiesa.moveToFirst()) {
                for(int i = 0; i < cursorChiesa.getCount(); i++){
                    String nameText = cursorChiesa.getString(0);
                    String opNameText = cursorChiesa.getString(1);
                    String ipAddressText = cursorChiesa.getString(2);
                    String sectionText = cursorChiesa.getString(3);
                    boolean state = (cursorChiesa.getInt(4) == 1);
                    int resourceId = cursorChiesa.getInt(5);

                    Light light = new Light(nameText, opNameText, ipAddressText, sectionText,
                            resourceId, state);
                    sectionFiveItems.add(light);
                    cursorChiesa.moveToNext();
                }
            }
            cursorChiesa.close();

            db.close();
        }
        catch(SQLException e) {
            Toast toast = Toast.makeText(getContext(),
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        // Fill section list
        sectionList.add(new Section(sectionOneName, sectionOneItems));
        sectionList.add(new Section(sectionTwoName, sectionTwoItems));
        sectionList.add(new Section(sectionThreeName, sectionThreeItems));
        sectionList.add(new Section(sectionFourName, sectionFourItems));
        sectionList.add(new Section(sectionFiveName, sectionFiveItems));
    }

    private class UpdateStateTask extends AsyncTask<Object, Void, Boolean> {
        private ContentValues stateValue;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //code that you want to run in a background thread
            String opName = (String) params[0];
            boolean state = (boolean) params[1];
            ContentValues stateValue = new ContentValues();
            stateValue.put("STATE", !state);
            SQLiteOpenHelper starbuzzDatabaseHelper = new DatabaseHelper(getContext());
            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("LIGHTS",
                        stateValue,
                        "OP_NAME = ?",
                        new String[]{opName});
                db.close();
                Log.e("NEW STATE", Boolean.toString(!state));
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
    }
}