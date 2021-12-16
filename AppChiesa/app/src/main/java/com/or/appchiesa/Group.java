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

    private static ArrayList<Light> firstScenario = new ArrayList<Light>(Arrays.asList(
            new Light("ch4", "ch4", "192.168.36.221", R.drawable.ic_bulb),
            new Light("ch5", "ch5", "192.168.36.221", R.drawable.ic_bulb),
            new Light("ch6", "ch10", "192.168.36.221", R.drawable.ic_bulb)
    ));

    public static List<Group> groups = new ArrayList<Group>(Arrays.asList(
            new Group("Camera",  R.drawable.ic_bulb_group, firstScenario),
            new Group("Salotto", R.drawable.ic_bulb_group, firstScenario),
            new Group("Salotto", R.drawable.ic_bulb_group, firstScenario)
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
