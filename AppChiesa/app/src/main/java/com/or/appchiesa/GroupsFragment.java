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

public class GroupsFragment extends Fragment {
    private SwitchFragment fragSwitch;
    private ChildRecyclerAdapter adapter;
    private Switch aSwitch;

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
        ArrayList<String> groupNames = new ArrayList<>();
        for (int i = 0; i < Group.groups.size(); i++) {
            groupNames.add(i, Group.groups.get(i).getName());
        }
        int[] groupImages = new int[Group.groups.size()];
        for (int i = 0; i < groupImages.length; i++) {
            groupImages[i] = Group.groups.get(i).getImageResourceId();
        }

        aSwitch = new Switch(getContext());

        this.adapter = new ChildRecyclerAdapter(groupNames, groupImages);
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
                                Group.groups.remove(position);
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
        Group clickedGroup = Group.groups.get(position);

        if(!(clickedGroup.getState())) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group_on);
            imageView.setImageDrawable(drawable);
            ArrayList<Light> groupLights = clickedGroup.getGroupLights();
            aSwitch.switchGroupOn(groupLights);
            clickedGroup.setState(true);
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_bulb_group);
            imageView.setImageDrawable(drawable);
            ArrayList<Light> groupLights = clickedGroup.getGroupLights();
            aSwitch.switchGroupOff(groupLights);
            clickedGroup.setState(false);
        }
    }
}

