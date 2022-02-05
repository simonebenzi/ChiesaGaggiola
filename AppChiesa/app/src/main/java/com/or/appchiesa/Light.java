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
    private String scenario;

    public Light(String name, String opName, String ipAddress, String section, String scenario, int imageResourceId, Boolean state) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
        this.section = section;
        this.state = state;
        this.scenario = scenario;
    }

    public Light(String name, String opName, String ipAddress, String section, String scenario, int imageResourceId) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.ipAddress = ipAddress;
        this.state = false;
        this.section = section;
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }

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

    private static String IP_ADDRESS = "192.168.1.124";

    public static List<Light> lights = new ArrayList<Light>(Arrays.asList(
            new Light("Illuminazione di servizio", "ch1", IP_ADDRESS, "Cappelle", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("ch1_1", "ch1_1", IP_ADDRESS, "Cappelle", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Bussola ingresso", "ch2", IP_ADDRESS, "Chiesa", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Faretti santi", "ch4", IP_ADDRESS, "Navata", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("LED navata", "ch5", IP_ADDRESS, "Navata", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Faretti cappelle", "ch10", IP_ADDRESS, "Cappelle", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Faretti coro alti", "ch12a", IP_ADDRESS, "Coro", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Faretti coro bassi", "ch12b", IP_ADDRESS, "Coro", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Prese presbiterio", "ch21", IP_ADDRESS, "Presbiterio", "Chiesa aperta", R.drawable.ic_bulb),
            new Light("Lampadari retro navata", "ch6a", IP_ADDRESS, "Navata", "Messa feriale", R.drawable.ic_bulb),
            new Light("Striscia LED cappelle", "ch9", IP_ADDRESS, "Cappelle", "Messa feriale", R.drawable.ic_bulb),
            new Light("Luci presbiterio", "ch11", IP_ADDRESS, "Presbiterio", "Messa feriale", R.drawable.ic_bulb),
            new Light("Faro alto presbiterio", "ch15", IP_ADDRESS, "Chiesa", "Messa feriale", R.drawable.ic_bulb),
            new Light("Lampadari fronte navata", "ch6b", IP_ADDRESS, "Navata", "Messa domenicale", R.drawable.ic_bulb),
            new Light("Cubotti fondo e navata", "ch3", IP_ADDRESS, "Chiesa", "Messa domenicale",  R.drawable.ic_bulb),
            new Light("ch6c", "ch6c", IP_ADDRESS, "Navata", "Messa solenne", R.drawable.ic_bulb),
            new Light("LED Via Crucis", "ch7", IP_ADDRESS, "Navata", "Messa solenne", R.drawable.ic_bulb)
    ));
}
