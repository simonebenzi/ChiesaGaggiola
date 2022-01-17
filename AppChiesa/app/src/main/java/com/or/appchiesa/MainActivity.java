package com.or.appchiesa;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

//public class MainActivity extends AppCompatActivity
//        implements AddLightDialogFragment.AddLightDialogInterface,
//        AddGroupDialogFragment.AddGroupDialogInterface,
//        ModifyGroupDialogFragment.ModifyGroupDialogInterface,
//        ModifyLightDialogFragment.ModifyLightDialogInterface,
//        GroupsFragment.SwitchFragment, TabLayoutMediator.TabConfigurationStrategy {
public class MainActivity extends AppCompatActivity
        implements TabLayoutMediator.TabConfigurationStrategy,
        GroupsFragment.SwitchFragment,
        ModifyGroupDialogFragment.ModifyGroupDialogInterface,
        ModifyLightDialogFragment.ModifyLightDialogInterface {

    private boolean isRotate = false;
    private final FragmentManager fm = getSupportFragmentManager();
    private GroupsFragment groupsFragment;
    private LightsFragment lightsFragment;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the view pager
        viewPager = findViewById(R.id.view_pager);
        setViewPagerAdapter();

        // Set the TabLayout
        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, this).attach();

        // Insert overflow menu un the toolbar
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set GroupsFragment as default
        /*this.lightsFragment = new LightsFragment();
        this.groupsFragment = new GroupsFragment();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, this.groupsFragment);
        transaction.commit();*/

        // Hide mini-FAB (add light/group buttons)
        final FloatingActionButton addGroupFab = (FloatingActionButton) findViewById(R.id.add_group);
        final FloatingActionButton addLightFab = (FloatingActionButton) findViewById(R.id.add_light);
        ViewAnimation.init(addGroupFab);
        ViewAnimation.init(addLightFab);

        // Add animation to FABs
        final FloatingActionButton mainFab = (FloatingActionButton) findViewById(R.id.add_floating_action_button);
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRotate = ViewAnimation.rotateFloatingButton(v, !isRotate);
                if (isRotate) {
                    ViewAnimation.showIn(addGroupFab);
                    ViewAnimation.showIn(addLightFab);
                } else {
                    ViewAnimation.showOut(addGroupFab);
                    ViewAnimation.showOut(addLightFab);
                }
            }
        });

        // Add listener to addRoom button
        addLightFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLightDialog();
                ViewAnimation.showOut(v);
                ViewAnimation.showOut(addGroupFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);

            }
        });

        addGroupFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAddGroupDialog();
                ViewAnimation.showOut(v);
                ViewAnimation.showOut(addLightFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);
            }
        });

        // Set buttons to switch on/off all lights
        /*Chip allOnChip = (Chip)findViewById(R.id.all_on_chip);
        Chip allOffChip = (Chip)findViewById(R.id.all_off_chip);

        allOnChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Light light:Light.lights){

                }
            }
        });

        allOffChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Light light:Light.lights){

                }
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayAddGroupDialog() {
        AddGroupDialogFragment addGroupDialogFragment =
                new AddGroupDialogFragment();
        addGroupDialogFragment.show(getSupportFragmentManager(), "add_group_dialog");
    }

    public void displayLightDialog() {
        AddLightDialogFragment addLightDialogFragment =
                new AddLightDialogFragment();
        addLightDialogFragment.show(getSupportFragmentManager(), "add_light_dialog");
    }

//    @Override
//    public void getLightInfos(String lightName, String ipAddress) {
//        Light.lights.add(new Light(lightName, ipAddress, R.drawable.ic_bulb));
//        RecyclerAdapter adapter = lightsFragment.getAdapter();
//        // If adapter never been initialized doesn't update
//        if(adapter != null)
//            adapter.updateRecycle("light");
//    }

    // Switch from groups to lights fragment clicking group card
    @Override
    public void groupFragmentSwitch() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, this.lightsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public void getGroupName(String group) {
//        Group.groups.add(new Group(group, R.drawable.ic_bulb_group));
//        RecyclerAdapter adapter = groupsFragment.getAdapter();
//        adapter.updateRecycle("group");
//    }

    @Override
    public void modifyGroupName(String groupName, int position) {
        Group.groups.get(position).setName(groupName);
        ChildRecyclerAdapter adapter = groupsFragment.getAdapter();
        adapter.updateRecycle("group");
    }

    @Override
    public void modifyLightDetails(String groupName, String ipAddress, int position) {
        Light.lights.get(position).setName(groupName);
        Light.lights.get(position).setIpAddress(ipAddress);
        ChildRecyclerAdapter adapter = lightsFragment.getMainRecyclerAdapter().getAdapter();
        adapter.updateRecycle("light");
    }

    private void setViewPagerAdapter(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        // Create an array of fragments
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GroupsFragment());
        fragments.add(new LightsFragment());
        // Connect ViewPager to the ViewPagerAdapter
        viewPagerAdapter.setData(fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.scenari));
        titles.add(getResources().getString(R.string.luci));
        tab.setText(titles.get(position));
    }
}