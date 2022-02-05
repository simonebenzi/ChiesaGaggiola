package com.or.appchiesa;

import android.content.Context;
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

public class ScenariosFragment extends Fragment {
    private SwitchFragment fragSwitch;
    private ChildRecyclerAdapter adapter;
    private Switch aSwitch;
    private DBHelper dbHelper;

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

        dbHelper = new DBHelper(getContext());

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
                                //Scenario.groups.remove(position);
                                getAdapter().updateRecycle("group");
                                //Toast.makeText(getContext(), "Delete group clicked", Toast.LENGTH_LONG).show();
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
        ModifyGroupDialogFragment modifyGroupDialogFragment =
                new ModifyGroupDialogFragment(position);
        modifyGroupDialogFragment.show(getFragmentManager(), "modify_group_dialog");
    }

    private void onClickGroup(int position, ImageView imageView) {
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        String scenario = scenariosName.get(position);

        ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();
        Boolean state = scenariosState.get(position);

        ArrayList<String> scenarioLights = dbHelper.getLightsOpNameFromScenario(scenario);
        ArrayList<String> scenarioIpAddress = dbHelper.getIpAddressFromScenario(scenario);

        if(!state) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group_on);
            imageView.setImageDrawable(drawable);
            aSwitch.switchGroupOn(scenarioLights, scenarioIpAddress);
            dbHelper.updateScenarioState(state, scenario);
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group_on);
            imageView.setImageDrawable(drawable);
            aSwitch.switchGroupOff(scenarioLights, scenarioIpAddress);
            dbHelper.updateScenarioState(state, scenario);
        }
    }
}

