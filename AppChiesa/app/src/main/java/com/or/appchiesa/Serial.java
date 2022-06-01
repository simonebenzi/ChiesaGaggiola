package com.or.appchiesa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Serial {
    private Context context;
    private UsbManager usbManager;
    private UsbDevice device;
    private UsbSerialDevice serialPort;
    private UsbDeviceConnection connection;
    private BroadcastReceiver broadcastReceiver;
    private UsbSerialInterface.UsbReadCallback mCallback;
    private Graphics graphics;
    private Actions actions;
    private DBHelper dbHelper;
    

    public final String ACTION_USB_PERMISSION = "com.or.serialcommunication.USB_PERMISSION";

    public interface Graphics {
        public void modifyToolbar(Context context, CharSequence text);

        public void enableStartButton(boolean state);
    }

    public interface Actions {
        public void start();

        public void write(String command);

        public void stop();
    }

    public Serial(Context context) {
        // Initialize context
        this.context = context;
        // Initialize DB Helper
        this.dbHelper = new DBHelper(context);
        // Initialize USB manager
        usbManager = (UsbManager) context.getSystemService(context.USB_SERVICE);
        graphics = (Graphics) this.context;
        actions = (Actions) this.context;

        // Defining a Callback which triggers whenever data is read.
        mCallback = new UsbSerialInterface.UsbReadCallback() {
            @Override
            public void onReceivedData(byte[] arg0) {
                String data = null;
                try {
                    data = new String(arg0, "UTF-8");
                    data.concat("/n");
                    //Log.e("RECEIVED DATA: ", data);
                    //graphics.showToast(context, data);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        };

        // Broadcast Receiver to automatically start and stop the Serial connection.
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                    boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                    if (granted) {
                        openConnection();
                    } else {
                        Log.d("SERIAL", "PERM NOT GRANTED");
                    }
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                    actions.start();
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                    actions.stop();
                }
            }
        };
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public UsbManager getUsbManager() {
        return usbManager;
    }

    public UsbDevice getDevice() {
        return device;
    }

    public void setDevice(UsbDevice device) {
        this.device = device;
    }

    public void setConnection(UsbDeviceConnection connection) {
        this.connection = connection;
    }

    public UsbSerialDevice getSerialPort() {
        return serialPort;
    }

    // Switch a light ON using the serial communication --> true-ON false-OFF
    public void lightSerialSwitch(String lightOpName, boolean state, ImageView imageView) {
        String stateStr, command;
        Drawable drawable;

        if (state) {
            stateStr = " ON";
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_on);
        } else {
            stateStr = " OFF";
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb);
        }
        command = lightOpName + stateStr;
        // Write the arduino command on the serial and update ImageView and DB
        actions.write(command);
        imageView.setImageDrawable(drawable);
        dbHelper.updateLightState(!state, lightOpName);
    }

    public void scenarioSerialSwitch(String scenario, ArrayList<String> lightsOpName, boolean state,
                                     ImageView imageView, ChildRecyclerAdapter adapter) {
        Drawable drawable;
        String scenarioCommand;

        if (state) {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_group_on);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_bulb_group);
        }
        // Update scenario
        imageView.setImageDrawable(drawable);
        dbHelper.updateScenarioState(!state, scenario);

        // Create the serial command for the scenario
        scenarioCommand = createScenarioCommands(lightsOpName, state);

        if (state) {
            // Get all other scenarios
            ArrayList<String> scenariosList = dbHelper.getScenariosExceptOne(scenario);

            // Update only lights that are not in common
            ArrayList<String> toSwitchOffLights = new ArrayList<>();
            for (int i = 0; i < scenariosList.size(); i++) {
                String scenarioName = scenariosList.get(i);
                // Switch off other scenarios
                dbHelper.updateScenarioState(true, scenarioName);

                String[] scenarioLightsArray = convertStringToArray(dbHelper.
                        getAllScenarioLights(scenarioName));
                ArrayList<String> scenarioLights = new ArrayList<>(Arrays.asList(scenarioLightsArray));
                for (int j = 0; j < scenarioLights.size(); j++) {
                    if (!(lightsOpName.contains(scenarioLights.get(j))) &&
                            !(toSwitchOffLights.contains(scenarioLights.get(j)))) {
                        toSwitchOffLights.add(scenarioLights.get(j));
                    }
                }
            }
            adapter.updateRecycle("group");

            String switchOffCommand = createScenarioCommands(toSwitchOffLights, false);
            scenarioCommand += switchOffCommand;
            Log.e("SCENARIO COMMAND: ", scenarioCommand);
            actions.write(scenarioCommand);
        } else {
            Log.e("SCENARIO COMMAND: ", scenarioCommand);
            actions.write(scenarioCommand);
        }
    }

    public void switchAllLights(ArrayList<String> allLightsOpName, ArrayList<String> scenariosName,
                                ArrayList<Boolean> lightsState, ArrayList<Boolean> scenariosState,
                                boolean state, ViewPagerAdapter adapter) {
        // Switch all lights on/off
        int size = scenariosName.size();

        String command = createScenarioCommands(allLightsOpName, state);
        Log.e("ALL LIGHTS COMMAND: ", command);
        actions.write(command);

        // Deactivate all scenarios

        for (int i = 0; i < size; i++) {
            String scenarioName = scenariosName.get(i);
            Boolean scenarioState = scenariosState.get(i);

            if (scenarioState)
                dbHelper.updateScenarioState(true, scenarioName);
        }

        // Try to notifyDataSetChanged in Lights Fragment main adapter
        LightsFragment lightsFragment = (LightsFragment) adapter.getFragments().get(1);
        try {
            lightsFragment.getMainRecyclerAdapter().notifyDataSetChanged();
        } catch (NullPointerException exception) {

        }

        // Try to notifyDataSetChanged in Scenarios Fragment adapter
        ScenariosFragment scenariosFragment = (ScenariosFragment) adapter.getFragments().get(0);
        try {
            scenariosFragment.getAdapter().updateRecycle("group");
        } catch (NullPointerException exception) {

        }
    }

    private String createScenarioCommands(ArrayList<String> lights, boolean state) {
        // List of commands
        String command = "";
        String stateStr;

        for (int i = 0; i < lights.size(); i++) {
            if (state) {
                stateStr = " ON";
            } else {
                stateStr = " OFF";
            }
            // Command to switch the single light on/off
            if ((i != (lights.size() - 1)) || state)
                command += lights.get(i) + stateStr + "\n";
            else
                command += lights.get(i) + stateStr;

            Boolean lightState = dbHelper.getLightState(lights.get(i));
            if (lightState != state)
                dbHelper.updateLightState(lightState, lights.get(i));
        }

        return command;
    }

    private static String[] convertStringToArray(String str) {
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }

    // Open the serial connection with Arduino
    public void openConnection() {
        connection = usbManager.openDevice(device);
        serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
        if (serialPort != null) {
            if (serialPort.open()) { //Set Serial Connection Parameters.
                graphics.enableStartButton(true);
                serialPort.setBaudRate(14400);
                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                serialPort.read(mCallback);
                String connectedStr = context.getResources().getString(R.string.connected);
                graphics.modifyToolbar(context, connectedStr);

            } else {
                Log.d("SERIAL", "PORT NOT OPEN");
            }
        } else {
            Log.d("SERIAL", "PORT IS NULL");
        }
    }
}
