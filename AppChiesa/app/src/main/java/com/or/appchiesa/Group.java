package com.or.appchiesa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {
    private String name;
    private int imageResourceId;
    private boolean state;
    private ArrayList<Light> groupLights;

    private static ArrayList<Light> scenario1 = new ArrayList<Light>(Arrays.asList(
            new Light("Illuminazione di servizio", "ch1", "192.168.20.221", "Cappelle", R.drawable.ic_bulb),
            new Light("ch1.1", "ch1.1", "192.168.20.221", "Cappelle",R.drawable.ic_bulb),
            new Light("Bussola ingresso", "ch2", "192.168.20.221", "Chiesa",R.drawable.ic_bulb),
            new Light("Faretti santi", "ch4", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("LED navata", "ch5", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("Faretti cappelle", "ch10", "192.168.20.221", "Cappelle",R.drawable.ic_bulb),
            new Light("Faretti coro alti", "ch12a", "192.168.20.221", "Coro",R.drawable.ic_bulb),
            new Light("Faretti coro bassi", "ch12b", "192.168.20.221", "Coro",R.drawable.ic_bulb),
            new Light("Prese presbiterio", "ch21", "192.168.20.221", "Presbiterio",R.drawable.ic_bulb)
    ));

    private static ArrayList<Light> scenario2 = new ArrayList<Light>(Arrays.asList(
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
            new Light("Faro alto presbiterio", "ch15", "192.168.20.221", "Chiesa",R.drawable.ic_bulb)
    ));

    private static ArrayList<Light> scenario3 = new ArrayList<Light>(Arrays.asList(
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
            new Light("Cubotti fondo e navata", "ch3", "192.168.20.221", "Chiesa",R.drawable.ic_bulb)
    ));

    private static ArrayList<Light> scenario4 = new ArrayList<Light>(Arrays.asList(
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

    private static ArrayList<Light> scenario5 = new ArrayList<Light>(Arrays.asList(
            new Light("Cubotti fondo e navata", "ch3", "192.168.20.221", "Chiesa",R.drawable.ic_bulb),
            new Light("LED navata", "ch5", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("LED Via Crucis", "ch7", "192.168.20.221", "Navata",R.drawable.ic_bulb),
            new Light("Striscia LED cappelle", "ch9", "192.168.20.221", "Cappelle",R.drawable.ic_bulb)
    ));

    public static List<Group> groups = new ArrayList<Group>(Arrays.asList(
            new Group("Chiesa aperta",  R.drawable.ic_bulb_group, scenario1),
            new Group("Messa feriale", R.drawable.ic_bulb_group, scenario2),
            new Group("Messa domenicale", R.drawable.ic_bulb_group, scenario3),
            new Group("Messa solenne", R.drawable.ic_bulb_group, scenario4),
            new Group("Via Crucis", R.drawable.ic_bulb_group, scenario5)
    ));


    public Group(String name, int imageResourceId, ArrayList<Light> groupLights) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.state = false;
        this.groupLights = groupLights;
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

    public boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public ArrayList<Light> getGroupLights() {
        return groupLights;
    }
}
