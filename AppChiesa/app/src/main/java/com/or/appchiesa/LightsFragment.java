package com.or.appchiesa;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LightsFragment extends Fragment {
    private RecyclerAdapter adapter;
    private Switch aSwitch;

    public RecyclerAdapter getAdapter() {
        return this.adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView lightsRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_lights,
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

        this.adapter = new RecyclerAdapter(lightNames, lightsImages);
        lightsRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        lightsRecycler.setLayoutManager(layoutManager);

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

        return lightsRecycler;
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
            aSwitch.switchOn(clickedLight.getIpAddress(), 8081);
            //Toast.makeText(getContext(), "Light switched on", Toast.LENGTH_LONG).show();
            clickedLight.setState(true);
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb);
            imageView.setImageDrawable(drawable);
            aSwitch.switchOff(clickedLight.getIpAddress(), 8081);
            //Toast.makeText(getContext(), "Light switched off", Toast.LENGTH_LONG).show();
            clickedLight.setState(false);
        }
    }
}