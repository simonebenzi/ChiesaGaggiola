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
    private String section;

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

    public String getSection() {
        return section;
    }

    public static List<Light> lights = new ArrayList<Light>(Arrays.asList(
            new Light("Illuminazione di servizio", "ch1", "192.168.20.221", "Cappelle", R.drawable.ic_bulb),
            new Light("ch1.1", "ch1.1", "192.168.20.221", "Cappelle",R.drawable.ic_bulb),
            new Light("Bussola ingresso", "ch2", "192.168.20.221", "Chiesa",R.drawable.ic_bulb),
            new Light("Faretti santi", "ch4", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("LED navata", "ch5", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("Faretti cappelle", "ch10", "192.168.20.221", "Cappelle",R.drawable.ic_bulb),
            new Light("Faretti coro alti", "ch12a", "192.168.20.221", "Coro",R.drawable.ic_bulb),
            new Light("Faretti coro bassi", "ch12b", "192.168.20.221", "Coro",R.drawable.ic_bulb),
            new Light("Prese presbiterio", "ch21", "192.168.20.221", "Presbiterio",R.drawable.ic_bulb),
            new Light("Lampadari retro navata", "ch6a", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("Striscia LED cappelle", "ch9", "192.168.20.221", "Cappelle",R.drawable.ic_bulb),
            new Light("Luci presbiterio", "ch11", "192.168.20.221", "Presbiterio",R.drawable.ic_bulb),
            new Light("Faro alto presbiterio", "ch15", "192.168.20.221", "Chiesa",R.drawable.ic_bulb),
            new Light("Lampadari fronte navata", "ch6b", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("Cubotti fondo e navata", "ch3", "192.168.20.221", "Chiesa",R.drawable.ic_bulb),
            new Light("ch6c", "ch6c", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("LED Via Crucis", "ch7", "192.168.20.221", "Navata",R.drawable.ic_bulb)
    ));

    public Light(String name, String opName, String ipAddress, String section, int imageResourceId, Boolean state) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
        this.section = section;
        this.state = state;
    }

    public Light(String name, String opName, String ipAddress, String section, int imageResourceId) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
        this.section = section;
    }
}
