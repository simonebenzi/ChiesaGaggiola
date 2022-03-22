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
            new Light("Faretti S.Francesco\ne Maragliano", "ch1_1", "Cappelle"),
            new Light("Luci ingresso bussola", "ch2", "Chiesa"),
            new Light("Faretti\nsanti", "ch4", "Navata"),
            new Light("Strisce LED\nnavata", "ch5", "Navata"),
            new Light("Faretti LED\ncappelle", "ch10", "Cappelle"),
            new Light("Faretti coro\nalti", "ch12a", "Coro"),
            new Light("Faretti coro\nbassi", "ch12b", "Coro"),
            new Light("Lampadari navata retro bassi", "ch6a", "Navata"),
            new Light("Striscia LED cappelle", "ch9", "Cappelle"),
            new Light("Luci\npresbiterio", "ch11", "Presbiterio"),
            new Light("Faro presbiterio centro alto", "ch15", "Presbiterio"),
            new Light("Lampadari navata fronte bassi", "ch6b", "Navata"),
            new Light("Cubotti fondo e navata", "ch3", "Chiesa"),
            new Light("Lampadari navata fronte/retro alti", "ch6c", "Navata"),
            new Light("LED\nVia Crucis", "ch7", "Navata"),
            new Light("Faretti LED quadri navata", "ch30", "Navata")
    ));
}
