package com.or.appchiesa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {
    private String name;
    private int imageResourceId;
    private boolean state;

    public static List<Group> groups = new ArrayList<Group>(Arrays.asList(
            new Group("Camera", R.drawable.ic_bulb_group),
            new Group("Salotto", R.drawable.ic_bulb_group)
    ));


    public Group(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.state = false;
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
}
