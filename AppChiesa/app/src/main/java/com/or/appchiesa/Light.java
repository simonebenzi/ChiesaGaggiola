package com.or.appchiesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Light {
    private String name;
    private String opName;
    private int imageResourceId;
    private String ipAddress;
    private Boolean state;

    public String getIpAddress() {
        return ipAddress;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getOpName() {
        return opName;
    }

    public static List<Light> lights = new ArrayList<Light>(Arrays.asList(
            new Light("Scrivania", "ch4", "192.168.36.221", R.drawable.ic_bulb),
            new Light("Lampadario", "ch5", "192.168.36.221", R.drawable.ic_bulb),
            new Light( "Comodino", "ch10","192.168.36.221", R.drawable.ic_bulb)
    ));

    public Light(String name, String opName, String ipAddress, int imageResourceId) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
    }
}
