package com.or.appchiesa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScenariosFragment extends Fragment {
    private SwitchFragment fragSwitch;
    private UpdateLightsRecycle updateLightsRecycleInter;

    private ChildRecyclerAdapter adapter;
    private Switch aSwitch;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    interface SwitchFragment {
        void groupFragmentSwitch();
    }

    interface UpdateLightsRecycle {
        void updateLightsRecycle();
    }

    public ChildRecyclerAdapter getAdapter() {
        return this.adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView groupRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_groups,
                container, false);

        // Initialize dbHelper
        dbHelper = new DBHelper(getContext());

        // Initialize database
        db = this.dbHelper.getWritableDatabase();

        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();

        ArrayList<Boolean> groupsState = dbHelper.getAllScenariosState();
        int stateSize = dbHelper.getAllScenariosState().size();
        int[] scenariosImages = new int[stateSize];
        for (int i = 0; i < stateSize; i++) {
            if (!(groupsState.get(i)))
                scenariosImages[i] = R.drawable.ic_bulb_group;
            else
                scenariosImages[i] = R.drawable.ic_bulb_group_on;

        }


        aSwitch = new Switch(getContext());

        this.adapter = new ChildRecyclerAdapter(getContext(), scenariosName, scenariosImages, "scenario");
        groupRecycler.setAdapter(this.adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        groupRecycler.setLayoutManager(layoutManager);

        this.adapter.setListener(new ChildRecyclerAdapter.Listener() {
            @Override
            public void onClickCard(int position, ImageView imageView) {
                onClickScenario(position, imageView);
            }

            @Override
            public void onClickPopup(final int position, View menuImage) {
                PopupMenu popupMenu = new PopupMenu(getContext(), menuImage);
                popupMenu.inflate(R.menu.group_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modify_item:
                                displayModifyGroupDialog(position);
                                //Toast.makeText(getContext(), "Rename group clicked", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.delete_item:
                                ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
                                String scenarioName = scenariosName.get(position);
                                displayPasswordDialog(scenarioName, db);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return groupRecycler;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragSwitch = (SwitchFragment) context;
        updateLightsRecycleInter = (UpdateLightsRecycle) context;
    }

    private void displayModifyGroupDialog(int position) {
        ModifyScenarioDialogFragment modifyScenarioDialogFragment =
                new ModifyScenarioDialogFragment(position);
        modifyScenarioDialogFragment.show(getFragmentManager(), "modify_group_dialog");
    }

    private void displayPasswordDialog(String scenario, SQLiteDatabase db) {
        PasswordDialogFragment passwordDialogFragment =
                new PasswordDialogFragment(scenario, "scenario", db);
        passwordDialogFragment.show(getFragmentManager(), "password_dialog");
    }

    private void onClickScenario(int position, ImageView imageView) {
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        String scenario = scenariosName.get(position);

        ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();
        Boolean state = scenariosState.get(position);

        String scenarioLightsStr = dbHelper.getAllScenarioLights(scenario);
        String[] scenarioLightsArray = convertStringToArray(scenarioLightsStr);
        ArrayList<String> scenarioLightsList = new ArrayList<>(Arrays.asList(scenarioLightsArray));

        String ipAddress = dbHelper.getIpAddress();

        if (!state) {
            aSwitch.switchScenarioOn(scenario, scenarioLightsList, ipAddress, imageView, getAdapter());
        } else {
            aSwitch.switchScenarioOff(scenario, scenarioLightsList, ipAddress, imageView);
        }

        updateLightsRecycleInter.updateLightsRecycle();
    }

    private static String[] convertStringToArray(String str) {
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}

