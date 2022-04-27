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
        public void showToast(Context context, CharSequence text);
        public void enableStartButton(boolean state);
    }

    public interface Actions {
        public void start();
        public void write(String command);
        //public void stop();
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
                    Log.e("RECEIVED DATA: ", data);
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
                        connection = usbManager.openDevice(device);
                        serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                        if (serialPort != null) {
                            if (serialPort.open()) { //Set Serial Connection Parameters.
                                graphics.enableStartButton(true);
                                serialPort.setBaudRate(9600);
                                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                                serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                                serialPort.read(mCallback);
                                graphics.showToast(context, "Serial Connection Opened!\n");

                            } else {
                                Log.d("SERIAL", "PORT NOT OPEN");
                            }
                        } else {
                            Log.d("SERIAL", "PORT IS NULL");
                        }
                    } else {
                        Log.d("SERIAL", "PERM NOT GRANTED");
                    }
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                    actions.start();
                } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                    //actions.stop();
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
    public void lightSerialSwitch(String lightName, String lightOpName, boolean state, ImageView imageView) {
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
//        this.getSerialPort().write(command.getBytes());
        imageView.setImageDrawable(drawable);
        dbHelper.updateLightState(!state, lightName, lightOpName);
    }
}
