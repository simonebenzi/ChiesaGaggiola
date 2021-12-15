package com.or.appchiesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Light {
    private String name;
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

    public static List<Light> lights = new ArrayList<Light>(Arrays.asList(
            new Light("Scrivania", "192.168.1.14", R.drawable.ic_bulb),
            new Light("Lampadario", "192.168.1.14", R.drawable.ic_bulb),
            new Light( "Comodino","192.168.1.14", R.drawable.ic_bulb)
    ));

    public Light(String name, String ipAddress, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
    }
}
