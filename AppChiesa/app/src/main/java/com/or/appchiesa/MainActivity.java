package com.or.appchiesa;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements TabLayoutMediator.TabConfigurationStrategy,
        ScenariosFragment.SwitchFragment,
        ModifyScenarioDialogFragment.ModifyGroupDialogInterface,
        ModifyLightDialogFragment.ModifyLightDialogInterface,
        AddScenarioDialogFragment.AddScenarioDialogInterface,
        AddLightDialogFragment.AddLightDialogInterface,
        ScenariosFragment.UpdateLightsRecycle,
        PasswordDialogFragment.PasswordDialogInterface,
        Serial.Graphics, Serial.Actions {

    private boolean isRotate = false;
    private final FragmentManager fm = getSupportFragmentManager();
    private ScenariosFragment scenariosFragment;
    private LightsFragment lightsFragment;
    private Switch aSwitch;
    private Serial aSerial;
    private DBHelper dbHelper;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> titles;

    private FloatingActionButton startSerialFab, stopSerialFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DBHelper
        this.dbHelper = new DBHelper(this);

        // Initialize switch
        aSwitch = new Switch(this);

        // Set the view pager
        viewPager = findViewById(R.id.view_pager);
        setViewPagerAdapter();

        // Set the TabLayout
        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, this).attach();

        // Insert overflow menu un the toolbar
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);

        // Hide mini-FAB (add light/group buttons)
        final FloatingActionButton addGroupFab = (FloatingActionButton) findViewById(R.id.add_group);
        final FloatingActionButton addLightFab = (FloatingActionButton) findViewById(R.id.add_light);
        startSerialFab = (FloatingActionButton) findViewById(R.id.start_serial);
        stopSerialFab = (FloatingActionButton) findViewById(R.id.stop_serial);
        ViewAnimation.init(addGroupFab);
        ViewAnimation.init(addLightFab);
        ViewAnimation.init(startSerialFab);
        ViewAnimation.init(stopSerialFab);

        // Add animation to FABs
        final FloatingActionButton mainFab = (FloatingActionButton) findViewById(R.id.add_floating_action_button);
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRotate = ViewAnimation.rotateFloatingButton(v, !isRotate);
                if (isRotate) {
                    ViewAnimation.showIn(addGroupFab);
                    ViewAnimation.showIn(addLightFab);
                    ViewAnimation.showIn(startSerialFab);
                    ViewAnimation.showIn(stopSerialFab);
                } else {
                    ViewAnimation.showOut(addGroupFab);
                    ViewAnimation.showOut(addLightFab);
                    ViewAnimation.showOut(startSerialFab);
                    ViewAnimation.showOut(stopSerialFab);
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
                ViewAnimation.showOut(startSerialFab);
                ViewAnimation.showOut(stopSerialFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);

            }
        });

        addGroupFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAddGroupDialog();
                ViewAnimation.showOut(v);
                ViewAnimation.showOut(addLightFab);
                ViewAnimation.showOut(startSerialFab);
                ViewAnimation.showOut(stopSerialFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);
            }
        });

        startSerialFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart(startSerialFab);
                ViewAnimation.showOut(v);
                ViewAnimation.showOut(addLightFab);
                ViewAnimation.showOut(addGroupFab);
                ViewAnimation.showOut(stopSerialFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);
            }
        });

        stopSerialFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStop(stopSerialFab);
                ViewAnimation.showOut(v);
                ViewAnimation.showOut(addLightFab);
                ViewAnimation.showOut(addGroupFab);
                ViewAnimation.showOut(startSerialFab);
                isRotate = ViewAnimation.rotateFloatingButton(mainFab, !isRotate);
            }
        });

        // Start connection
        aSerial = new Serial(this);
        enableStartButton(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(aSerial.ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(aSerial.getBroadcastReceiver(), filter);
        start();
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
        AddScenarioDialogFragment addScenarioDialogFragment =
                new AddScenarioDialogFragment();
        addScenarioDialogFragment.show(getSupportFragmentManager(), "add_group_dialog");
    }

    public void displayLightDialog() {
        AddLightDialogFragment addLightDialogFragment =
                new AddLightDialogFragment();
        addLightDialogFragment.show(getSupportFragmentManager(), "add_light_dialog");
    }

    public void displayUpdateIpAddressDialog() {
        UpdateIpAddressDialogFragment updateIpAddressDialogFragment =
                new UpdateIpAddressDialogFragment();
        updateIpAddressDialogFragment.show(getSupportFragmentManager(), "add_light_dialog");
    }

    @Override
    public void getLightInfos(String lightName, String opName, String section) {
        // Insert new light in Database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.insertLight(db, lightName, opName, section, false);
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        LightsFragment fragment = (LightsFragment) adapter.getFragments().get(1);
        MainRecyclerAdapter mainRecyclerAdapter = fragment.getMainRecyclerAdapter();
        if (mainRecyclerAdapter != null)
            mainRecyclerAdapter.notifyDataSetChanged();
    }

    // Switch from groups to lights fragment clicking group card
    @Override
    public void groupFragmentSwitch() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, this.lightsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void getGroupDetails(String scenarioName, boolean[] selectedLights) {
        // Get selected lights
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection();
        ArrayList<String> selectedLightsOpNameList = new ArrayList<>();
        for (int i = 0; i < selectedLights.length; i++) {
            if (selectedLights[i])
                selectedLightsOpNameList.add(lightsOpName.get(i));
        }
        String[] selectedLightsOpNameArray = (String[]) selectedLightsOpNameList
                .toArray(new String[selectedLightsOpNameList.size()]);
        // Insert new scenario in Database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.insertScenario(db, scenarioName, false, selectedLightsOpNameArray);
        // Update recycler view
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        ScenariosFragment fragment = (ScenariosFragment) adapter.getFragments().get(0);
        fragment.getAdapter().updateRecycle("group");
    }

    @Override
    public void modifyGroupName(String newName, int position, boolean[] selectedLights) {
        String oldName = dbHelper.getAllScenariosName().get(position);
        // Get selected lights
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection();
        ArrayList<String> selectedLightsOpNameList = new ArrayList<>();
        for (int i = 0; i < selectedLights.length; i++) {
            if (selectedLights[i])
                selectedLightsOpNameList.add(lightsOpName.get(i));
        }
        String[] selectedLightsOpNameArray = (String[]) selectedLightsOpNameList
                .toArray(new String[selectedLightsOpNameList.size()]);

        dbHelper.updateScenarioLights(oldName, selectedLightsOpNameArray);
        dbHelper.updateScenarioName(oldName, newName);
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        ScenariosFragment fragment = (ScenariosFragment) adapter.getFragments().get(0);
        fragment.getAdapter().updateRecycle("group");
    }

    @Override
    public void modifyLightDetails(String newLightName, int position, String oldSection, String newSection, String newLightOpName) {
        String name, opName;
        ArrayList<String> lightsName = dbHelper.getAllLightsNameFromSection(oldSection);
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection(oldSection);
        name = lightsName.get(position);
        opName = lightsOpName.get(position);

        dbHelper.updateLightSection(name, newSection);
        dbHelper.updateLightName(name, newLightName, opName);
        dbHelper.updateLightOpName(name, newLightOpName);

        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        LightsFragment fragment = (LightsFragment) adapter.getFragments().get(1);
        fragment.getMainRecyclerAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateLightRecycler() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        LightsFragment fragment = (LightsFragment) adapter.getFragments().get(1);
        fragment.getMainRecyclerAdapter().notifyDataSetChanged();
    }

    private void setViewPagerAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        // Create an array of fragments
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ScenariosFragment());
        fragments.add(new LightsFragment());

        // Connect ViewPager to the ViewPagerAdapter
        viewPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.scenari));
        titles.add(getResources().getString(R.string.luci));
        tab.setText(titles.get(position));
    }

    public void onClickCloseAll(View view) {
        ArrayList<Boolean> lightsState = dbHelper.getAllLightsState();
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection();
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();

        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        //aSwitch.switchAllLightsOff(ipAddress, lightsName, lightsOpName, scenariosName, lightsState,
                //scenariosState, adapter);
        aSerial.switchAllLights(lightsOpName, scenariosName, lightsState, scenariosState, false,
                adapter);
    }

    public void onClickOpenAll(View view) {
        ArrayList<Boolean> lightsState = dbHelper.getAllLightsState();
        ArrayList<String> lightsOpName = dbHelper.getAllLightsOpNameFromSection();
        ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
        ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();

        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        //aSwitch.switchAllLightsOn(ipAddress, lightsName, lightsOpName, scenariosName, lightsState,
                //scenariosState, adapter);
        aSerial.switchAllLights(lightsOpName, scenariosName, lightsState, scenariosState, true,
                adapter);
    }

    @Override
    public void updateLightsRecycle() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        LightsFragment fragment = (LightsFragment) adapter.getFragments().get(1);
        try {
            fragment.getMainRecyclerAdapter().notifyDataSetChanged();
        } catch (NullPointerException exception) {

        }
    }

    @Override
    public void updateScenarioRecycler() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        ScenariosFragment fragment = (ScenariosFragment) adapter.getFragments().get(0);
        try {
            fragment.getAdapter().updateRecycle("group");
        } catch (NullPointerException exception) {

        }
    }

    @Override
    public void enableStartButton(boolean state) {
        startSerialFab.setEnabled(!state);
        stopSerialFab.setEnabled(state);
    }

    @Override
    public void showToast(Context context, CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    public void onClickStart(View view) {
        HashMap<String, UsbDevice> usbDevices = aSerial.getUsbManager().getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                aSerial.setDevice(entry.getValue());
                int deviceVID = aSerial.getDevice().getVendorId();
                if (deviceVID == 0x2341)//Arduino Vendor ID
                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                            new Intent(aSerial.ACTION_USB_PERMISSION), 0);
                    aSerial.getUsbManager().requestPermission(aSerial.getDevice(), pi);
                    keep = false;
                } else {
                    aSerial.setConnection(null);
                    aSerial.setDevice(null);
                }

                if (!keep)
                    break;
            }
        } else {
            CharSequence text = "Sistema non connesso a USB!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

    @Override
    public void start() {
        onClickStart(startSerialFab);
    }

    @Override
    public void stop() {
        onClickStop(stopSerialFab);
    }

    @Override
    public void write(String command) {
        aSerial.getSerialPort().write(command.getBytes());
    }

    public void onClickStop(View view) {
        enableStartButton(false);
        aSerial.getSerialPort().close();
        showToast(this, "Sistema disconnesso da USB!");
    }
}