package com.or.appchiesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Light {
    private String name;
    private String opName;
    private int imageResourceId;
    private Boolean state;
    private String section;

    public Light(String name, String opName, String section, int imageResourceId, Boolean state) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.state = false;
        this.section = section;
        this.state = state;
    }

    public Light(String name, String opName, String section, int imageResourceId) {
        this.name = name;
        this.opName = opName;
        this.imageResourceId = imageResourceId;
        this.state = false;
        this.section = section;
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
            new Light("Illuminazione di servizio", "ch1", "Cappelle", R.drawable.ic_bulb),
            new Light("ch1_1", "ch1_1", "Cappelle", R.drawable.ic_bulb),
            new Light("Bussola ingresso", "ch2", "Chiesa", R.drawable.ic_bulb),
            new Light("Faretti santi", "ch4", "Navata", R.drawable.ic_bulb),
            new Light("LED navata", "ch5", "Navata", R.drawable.ic_bulb),
            new Light("Faretti cappelle", "ch10", "Cappelle", R.drawable.ic_bulb),
            new Light("Faretti coro alti", "ch12a", "Coro", R.drawable.ic_bulb),
            new Light("Faretti coro bassi", "ch12b", "Coro", R.drawable.ic_bulb),
            new Light("Prese presbiterio", "ch21", "Presbiterio", R.drawable.ic_bulb),
            new Light("Lampadari retro navata", "ch6a", "Navata", R.drawable.ic_bulb),
            new Light("Striscia LED cappelle", "ch9", "Cappelle", R.drawable.ic_bulb),
            new Light("Luci presbiterio", "ch11", "Presbiterio", R.drawable.ic_bulb),
            new Light("Faro alto presbiterio", "ch15", "Chiesa", R.drawable.ic_bulb),
            new Light("Lampadari fronte navata", "ch6b", "Navata", R.drawable.ic_bulb),
            new Light("Cubotti fondo e navata", "ch3", "Chiesa", R.drawable.ic_bulb),
            new Light("ch6c", "ch6c", "Navata", R.drawable.ic_bulb),
            new Light("LED Via Crucis", "ch7", "Navata", R.drawable.ic_bulb)
    ));
}
