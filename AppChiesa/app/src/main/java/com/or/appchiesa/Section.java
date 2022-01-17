package com.or.appchiesa;

import java.util.ArrayList;

public class Section {
    private String name;
    private ArrayList<Light> items;

    public Section(String name, ArrayList<Light> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Light> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return name;
    }
}
