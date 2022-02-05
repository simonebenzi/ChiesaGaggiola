package com.or.appchiesa;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
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

    private ArrayList<Section> sectionList = new ArrayList<>();

    public MainRecyclerAdapter getMainRecyclerAdapter() {
        return this.mainRecyclerAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView mainRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_lights,
                container, false);

        // Initialize switch
        aSwitch = new Switch(getContext());

        this.mainRecyclerAdapter = new MainRecyclerAdapter(sectionList, getContext());
        // Initialize sections with corresponding lights
        this.dbHelper = new DBHelper(getContext());
        initSections();
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
        ArrayList<String> sectionOneNames = dbHelper.getLightsNameFromSection(sectionOneName);
        ArrayList<String> sectionOneOpNames = dbHelper.getLightsOpNameFromSection(sectionOneName);
        ArrayList<String> sectionOneIpAddress = dbHelper.getLightsIpAddressFromSection(sectionOneName);
        ArrayList<String> sectionOneScenario = dbHelper.getLightsScenarioFromSection(sectionOneName);
        ArrayList<Boolean> sectionOneState = dbHelper.getLightsStateFromSection(sectionOneName);
        ArrayList<Light> sectionOneItems = new ArrayList<>();
        for(int i = 0; i < sectionOneNames.size(); i++){
            int resourceId;
            if(!(sectionOneState.get(i))){
                resourceId = R.drawable.ic_bulb;
            }
            else {
                resourceId = R.drawable.ic_bulb_on;
            }
            sectionOneItems.add(new Light(
                 sectionOneNames.get(i),
                 sectionOneOpNames.get(i),
                 sectionOneIpAddress.get(i),
                 sectionOneName,
                 sectionOneScenario.get(i),
                 resourceId,
                 sectionOneState.get(i)
            ));
        }

        String sectionTwoName = "Cappelle";
        ArrayList<String> sectionTwoNames = dbHelper.getLightsNameFromSection(sectionTwoName);
        ArrayList<String> sectionTwoOpNames = dbHelper.getLightsOpNameFromSection(sectionTwoName);
        ArrayList<String> sectionTwoIpAddress = dbHelper.getLightsIpAddressFromSection(sectionTwoName);
        ArrayList<String> sectionTwoScenario = dbHelper.getLightsScenarioFromSection(sectionTwoName);
        ArrayList<Boolean> sectionTwoState = dbHelper.getLightsStateFromSection(sectionTwoName);
        ArrayList<Light> sectionTwoItems = new ArrayList<>();
        for(int i = 0; i < sectionTwoNames.size(); i++){
            int resourceId;
            if(!(sectionTwoState.get(i))){
                resourceId = R.drawable.ic_bulb;
            }
            else {
                resourceId = R.drawable.ic_bulb_on;
            }
            sectionTwoItems.add(new Light(
                    sectionTwoNames.get(i),
                    sectionTwoOpNames.get(i),
                    sectionTwoIpAddress.get(i),
                    sectionTwoName,
                    sectionTwoScenario.get(i),
                    resourceId,
                    sectionTwoState.get(i)
            ));
        }

        String sectionThreeName = "Presbiterio";
        ArrayList<String> sectionThreeNames = dbHelper.getLightsNameFromSection(sectionThreeName);
        ArrayList<String> sectionThreeOpNames = dbHelper.getLightsOpNameFromSection(sectionThreeName);
        ArrayList<String> sectionThreeIpAddress = dbHelper.getLightsIpAddressFromSection(sectionThreeName);
        ArrayList<String> sectionThreeScenario = dbHelper.getLightsScenarioFromSection(sectionThreeName);
        ArrayList<Boolean> sectionThreeState = dbHelper.getLightsStateFromSection(sectionThreeName);
        ArrayList<Light> sectionThreeItems = new ArrayList<>();
        for(int i = 0; i < sectionThreeNames.size(); i++){
            int resourceId;
            if(!(sectionThreeState.get(i))){
                resourceId = R.drawable.ic_bulb;
            }
            else {
                resourceId = R.drawable.ic_bulb_on;
            }
            sectionThreeItems.add(new Light(
                    sectionThreeNames.get(i),
                    sectionThreeOpNames.get(i),
                    sectionThreeIpAddress.get(i),
                    sectionThreeName,
                    sectionThreeScenario.get(i),
                    resourceId,
                    sectionThreeState.get(i)
            ));
        }

        String sectionFourName = "Coro";
        ArrayList<String> sectionFourNames = dbHelper.getLightsNameFromSection(sectionFourName);
        ArrayList<String> sectionFourOpNames = dbHelper.getLightsOpNameFromSection(sectionFourName);
        ArrayList<String> sectionFourIpAddress = dbHelper.getLightsIpAddressFromSection(sectionFourName);
        ArrayList<String> sectionFourScenario = dbHelper.getLightsScenarioFromSection(sectionFourName);
        ArrayList<Boolean> sectionFourState = dbHelper.getLightsStateFromSection(sectionFourName);
        ArrayList<Light> sectionFourItems = new ArrayList<>();
        for(int i = 0; i < sectionFourNames.size(); i++){
            int resourceId;
            if(!(sectionFourState.get(i))){
                resourceId = R.drawable.ic_bulb;
            }
            else {
                resourceId = R.drawable.ic_bulb_on;
            }
            sectionFourItems.add(new Light(
                    sectionFourNames.get(i),
                    sectionFourOpNames.get(i),
                    sectionFourIpAddress.get(i),
                    sectionFourName,
                    sectionFourScenario.get(i),
                    resourceId,
                    sectionFourState.get(i)
            ));
        }

        String sectionFiveName = "Chiesa";
        ArrayList<String> sectionFiveNames = dbHelper.getLightsNameFromSection(sectionFiveName);
        ArrayList<String> sectionFiveOpNames = dbHelper.getLightsOpNameFromSection(sectionFiveName);
        ArrayList<String> sectionFiveIpAddress = dbHelper.getLightsIpAddressFromSection(sectionFiveName);
        ArrayList<String> sectionFiveScenario = dbHelper.getLightsScenarioFromSection(sectionFiveName);
        ArrayList<Boolean> sectionFiveState = dbHelper.getLightsStateFromSection(sectionFiveName);
        ArrayList<Light> sectionFiveItems = new ArrayList<>();
        for(int i = 0; i < sectionFiveNames.size(); i++){
            int resourceId;
            if(!(sectionFiveState.get(i))){
                resourceId = R.drawable.ic_bulb;
            }
            else {
                resourceId = R.drawable.ic_bulb_on;
            }
            sectionFiveItems.add(new Light(
                    sectionFiveNames.get(i),
                    sectionFiveOpNames.get(i),
                    sectionFiveIpAddress.get(i),
                    sectionFiveName,
                    sectionFiveScenario.get(i),
                    resourceId,
                    sectionFiveState.get(i)
            ));
        }


        // Fill section list
        sectionList.add(new Section(sectionOneName, sectionOneItems));
        sectionList.add(new Section(sectionTwoName, sectionTwoItems));
        sectionList.add(new Section(sectionThreeName, sectionThreeItems));
        sectionList.add(new Section(sectionFourName, sectionFourItems));
        sectionList.add(new Section(sectionFiveName, sectionFiveItems));
    }
}