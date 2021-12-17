package com.or.appchiesa;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LightsFragment extends Fragment {
    private RecyclerAdapter adapter;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private Switch aSwitch;

    private ArrayList<Section> sectionList = new ArrayList<>();

    public RecyclerAdapter getAdapter() {
        return this.adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();

        RecyclerView mainRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_lights,
                container, false);
        String[] lightNames = new String[Light.lights.size()];
        for(int i = 0; i < lightNames.length; i++){
            lightNames[i] = Light.lights.get(i).getName();
        }
        int[] lightsImages = new int[Light.lights.size()];
        for(int i = 0; i < lightsImages.length; i++){
            lightsImages[i] = Light.lights.get(i).getImageResourceId();
        }

        // Initialize switch
        aSwitch = new Switch(getContext());

        this.mainRecyclerAdapter = new MainRecyclerAdapter(sectionList);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        this.adapter.setListener(new RecyclerAdapter.Listener() {
            @Override
            public void onClickCard(int position, ImageView imageView) {
                onClickLight(position, imageView);
            }

            @Override
            public void onClickPopup(final int position, View menuImage) {
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
                                getAdapter().updateRecycle("light");
                                //Toast.makeText(getContext(), "Delete light clicked", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return mainRecyclerView;
    }

    private void displayModifyLightDialog(int position) {
        ModifyLightDialogFragment modifyLightDialogFragment =
                new ModifyLightDialogFragment(position);
        modifyLightDialogFragment.show(getFragmentManager(), "modify_light_dialog");
    }

    private void onClickLight(int position, ImageView imageView) {
        Light clickedLight = Light.lights.get(position);

        if(!(clickedLight.getState())) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_on);
            imageView.setImageDrawable(drawable);
            aSwitch.switchLightOn(clickedLight.getIpAddress(), clickedLight.getOpName());
            //Toast.makeText(getContext(), "Light switched on", Toast.LENGTH_LONG).show();
            clickedLight.setState(true);
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb);
            imageView.setImageDrawable(drawable);
            aSwitch.switchLightOff(clickedLight.getIpAddress(), clickedLight.getOpName());
            //Toast.makeText(getContext(), "Light switched off", Toast.LENGTH_LONG).show();
            clickedLight.setState(false);
        }
    }

    private void initData() {
        String sectionOneName = "Navata";
        ArrayList<String> sectionOneItems = new ArrayList<String>();
        sectionOneItems.add("ch4");
        sectionOneItems.add("ch5");
        sectionOneItems.add("ch3");

        String sectionTwoName = "Navata";
        ArrayList<String> sectionTwoItems = new ArrayList<String>();
        sectionTwoItems.add("ch7");

        sectionList.add(new Section(sectionOneName, sectionOneItems));
        sectionList.add(new Section(sectionTwoName, sectionTwoItems));
    }
}