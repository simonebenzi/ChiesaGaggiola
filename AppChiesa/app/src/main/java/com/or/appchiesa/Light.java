package com.or.appchiesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Light {
    private String name;
    private String opName;
    private Boolean state;
    private String section;

    public Light(String name, String opName, String section, Boolean state) {
        this.name = name;
        this.opName = opName;
        this.state = false;
        this.section = section;
        this.state = state;
    }

    public Light(String name, String opName, String section) {
        this.name = name;
        this.opName = opName;
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


    public String getOpName() {
        return opName;
    }

    public String getSection() {
        return section;
    }

    public static List<Light> lights = new ArrayList<Light>(Arrays.asList(
            new Light("Illuminazione di servizio", "ch1", "Cappelle"),
            new Light("ch1_1", "ch1_1", "Cappelle"),
            new Light("Bussola ingresso", "ch2", "Chiesa"),
            new Light("Faretti santi", "ch4", "Navata"),
            new Light("LED navata", "ch5", "Navata"),
            new Light("Faretti cappelle", "ch10", "Cappelle"),
            new Light("Faretti coro alti", "ch12a", "Coro"),
            new Light("Faretti coro bassi", "ch12b", "Coro"),
            new Light("Prese presbiterio", "ch21", "Presbiterio"),
            new Light("Lampadari retro navata", "ch6a", "Navata"),
            new Light("Striscia LED cappelle", "ch9", "Cappelle"),
            new Light("Luci presbiterio", "ch11", "Presbiterio"),
            new Light("Faro alto presbiterio", "ch15", "Chiesa"),
            new Light("Lampadari fronte navata", "ch6b", "Navata"),
            new Light("Cubotti fondo e navata", "ch3", "Chiesa"),
            new Light("ch6c", "ch6c", "Navata"),
            new Light("LED Via Crucis", "ch7", "Navata")
    ));
}
