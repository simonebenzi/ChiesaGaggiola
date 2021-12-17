package com.or.appchiesa;

import java.util.ArrayList;

public class Section {
    private String name;
    private ArrayList<String> items;

    public Section(String name, ArrayList<String> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return name;
    }
}
