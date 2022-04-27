package com.or.appchiesa;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
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
    private Serial serialSwitch;

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
        // Initialize serial switch
        serialSwitch = new Serial(getContext());

        this.mainRecyclerAdapter = new MainRecyclerAdapter(sectionList, getContext());
        // Initialize sections with corresponding lights
        this.mainRecyclerAdapter.setClickListener(new MainRecyclerAdapter.ClickListener() {
            @Override
            public void onClickLight(int position, ImageView imageView, String sectionName) {
                Boolean state;
                String opName, ipAddress, name;

                ArrayList<Boolean> lightsState = dbHelper.getAllLightsState(sectionName);
                ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection(sectionName);
                ipAddress = dbHelper.getIpAddress();
                ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection(sectionName);

                state = lightsState.get(position);
                opName = lightsOpName.get(position);
                name = lightsName.get(position);

                if (!state) {
//                    aSwitch.switchLightOn(ipAddress, name, opName, imageView);
                    serialSwitch.lightSerialSwitch(name, opName, true, imageView);
                } else {
//                    aSwitch.switchLightOff(ipAddress, name, opName, imageView);
                    serialSwitch.lightSerialSwitch(name, opName, false, imageView);
                }
            }

            public void onItemClick(int position, View menuImage, String section) {
                PopupMenu popupMenu = new PopupMenu(getContext(), menuImage);
                popupMenu.inflate(R.menu.light_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modify_item:
                                displayModifyLightDialog(position, section);
                                break;
                            case R.id.delete_item:
                                ArrayList<String> lightsName = dbHelper
                                        .getAllLightsNameFromSection(section);
                                String lightName = lightsName.get(position);
                                displayPasswordDialog(lightName, db);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

//        mainRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.OnItemTouchListener scrollTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        mainRecyclerView.addOnItemTouchListener(scrollTouchListener);

        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return mainRecyclerView;
    }

    private void displayModifyLightDialog(int position, String section) {
        ModifyLightDialogFragment modifyLightDialogFragment =
                new ModifyLightDialogFragment(position, section);
        modifyLightDialogFragment.show(getFragmentManager(), "modify_light_dialog");
    }

    private void displayPasswordDialog(String light, SQLiteDatabase db) {
        PasswordDialogFragment passwordDialogFragment =
                new PasswordDialogFragment(light, "light", db);
        passwordDialogFragment.show(getFragmentManager(), "password_dialog");
    }
}