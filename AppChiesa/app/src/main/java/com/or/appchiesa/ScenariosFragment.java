package com.or.appchiesa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScenariosFragment extends Fragment {
    private SwitchFragment fragSwitch;
    private ChildRecyclerAdapter adapter;
    private Switch aSwitch;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    interface SwitchFragment {
        void groupFragmentSwitch();
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
        for(int i = 0; i < stateSize; i++){
            if(!(groupsState.get(i)))
                scenariosImages[i] = R.drawable.ic_bulb_group;
            else
                scenariosImages[i] = R.drawable.ic_bulb_group_on;

        }


        aSwitch = new Switch(getContext());

        this.adapter = new ChildRecyclerAdapter(getContext(), scenariosName, scenariosImages);
        groupRecycler.setAdapter(this.adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        groupRecycler.setLayoutManager(layoutManager);

        this.adapter.setListener(new ChildRecyclerAdapter.Listener() {
            @Override
            public void onClickCard(int position, ImageView imageView) {
                onClickGroup(position, imageView);
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
                                dbHelper.deleteScenario(db, scenarioName);
                                getAdapter().updateRecycle("group");
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
    }

    private void displayModifyGroupDialog(int position) {
        ModifyScenarioDialogFragment modifyScenarioDialogFragment =
                new ModifyScenarioDialogFragment(position);
        modifyScenarioDialogFragment.show(getFragmentManager(), "modify_group_dialog");
    }

    private void onClickGroup(int position, ImageView imageView) {
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        String scenario = scenariosName.get(position);

        ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();
        Boolean state = scenariosState.get(position);

        ArrayList<String> scenariosLights = dbHelper.getAllScenariosLights();
        String aScenarioLights = scenariosLights.get(position);
        String[] scenarioLightsArray = convertStringToArray(aScenarioLights);
        ArrayList<String> scenarioLightsList = new ArrayList<>(Arrays.asList(scenarioLightsArray));
        ArrayList<String> scenarioIpAddress = new ArrayList<>();
        for(int i = 0; i < scenarioLightsList.size(); i++) {
            scenarioIpAddress.add(i, dbHelper
                    .getLightIpAddressFromOpName(scenarioLightsList.get(i)));
        }

        if(!state) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group_on);
            imageView.setImageDrawable(drawable);
            for(int i = 0; i < scenarioLightsList.size(); i++){
                Boolean lightState = dbHelper.getLightState(scenarioLightsList.get(i));
                if(!lightState)
                    dbHelper.updateLightState(lightState, scenarioLightsList.get(i));
            }
            aSwitch.switchScenarioOn(scenarioLightsList, scenarioIpAddress);
            dbHelper.updateScenarioState(state, scenario);
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group);
            imageView.setImageDrawable(drawable);
            for(int i = 0; i < scenarioLightsList.size(); i++){
                Boolean lightState = dbHelper.getLightState(scenarioLightsList.get(i));
                if(lightState)
                    dbHelper.updateLightState(lightState, scenarioLightsList.get(i));
            }
            aSwitch.switchScenarioOff(scenarioLightsList, scenarioIpAddress);
            dbHelper.updateScenarioState(state, scenario);
        }
    }

    private static String[] convertStringToArray(String str){
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}

