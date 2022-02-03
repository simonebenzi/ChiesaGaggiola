package com.or.appchiesa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scenario {
    private String name;
    private Boolean state;

    public Scenario(String name, Boolean state) {
        this.name = name;
        this.state = state;
    }

    public Scenario(String name) {
        this.name = name;
        this.state = false;
    }

    public String getName() {
        return name;
    }

    public Boolean getState() {
        return state;
    }

    public static ArrayList<Scenario> scenariosArray = new ArrayList<>(Arrays.asList(
            new Scenario("Chiesa aperta"),
            new Scenario("Messa feriale"),
            new Scenario("Messa domenicale"),
            new Scenario("Messa solenne"),
            new Scenario("Via Crucis")));
}
