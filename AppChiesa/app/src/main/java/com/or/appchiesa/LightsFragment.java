package com.or.appchiesa;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LightsFragment extends Fragment {

    private MainRecyclerAdapter mainRecyclerAdapter;
    private Switch aSwitch;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private ArrayList<String> sectionList = new ArrayList<>();

    public MainRecyclerAdapter getMainRecyclerAdapter() {
        return this.mainRecyclerAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView mainRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_lights,
                container, false);

        // Initialize dbHelper
        this.dbHelper = new DBHelper(getContext());

        // Initialize database
        db = this.dbHelper.getWritableDatabase();

        // Initialize section list
        sectionList = dbHelper.getAllSectionsName();

        // Initialize switch
        aSwitch = new Switch(getContext());

        this.mainRecyclerAdapter = new MainRecyclerAdapter(sectionList, getContext());
        // Initialize sections with corresponding lights
        this.mainRecyclerAdapter.setClickListener(new MainRecyclerAdapter.ClickListener() {
            @Override
            public void onClickLight(int position, ImageView imageView, String sectionName) {
                Drawable drawable;
                Boolean state;
                String opName, ipAddress, name;

                ArrayList<Boolean> lightsState = dbHelper.getAllLightsState(sectionName);
                ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection(sectionName);
                ArrayList<String> lightsIpAddress = dbHelper.getAllLightsIpAddressFromSection(sectionName);
                ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection(sectionName);

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

            public void onItemClick(int position, View menuImage, String section) {
                PopupMenu popupMenu = new PopupMenu(getContext(), menuImage);
                popupMenu.inflate(R.menu.group_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modify_item:
                                displayModifyLightDialog(position, section);
                                //Toast.makeText(getContext(), "Rename light clicked", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.delete_item:
                                ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection();
                                String lightName = lightsName.get(position);
                                dbHelper.deleteLight(db, lightName);
                                mainRecyclerAdapter.notifyDataSetChanged();
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

    public void displayModifyLightDialog(int position, String section) {
        ModifyLightDialogFragment modifyLightDialogFragment =
                new ModifyLightDialogFragment(position, section);
        modifyLightDialogFragment.show(getFragmentManager(), "modify_light_dialog");
    }
}