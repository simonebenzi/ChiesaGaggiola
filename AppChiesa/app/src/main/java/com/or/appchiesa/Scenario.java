package com.or.appchiesa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scenario {
    private String name;
    private Boolean state;
    private ArrayList<String> lightsOpName;

    public Scenario(String name, Boolean state, ArrayList<String> lightsOpName) {
        this.name = name;
        this.state = state;
        this.lightsOpName = lightsOpName;
    }

    public Scenario(String name, ArrayList<String> lightsOpName) {
        this.name = name;
        this.state = false;
        this.lightsOpName = lightsOpName;
    }

    public String getName() {
        return name;
    }

    public Boolean getState() {
        return state;
    }

    public ArrayList<String> getLightsOpName() {
        return lightsOpName;
    }

    // Tolto in tutti gli scenari CH.21
    private static ArrayList<String> chiesaAperta = new ArrayList<>(Arrays.asList(
            "ch1", "ch1_1", "ch2", "ch4", "ch5", "ch10", "ch12a", "ch12b"
    ));

    private static ArrayList<String> messaFeriale = new ArrayList<>(Arrays.asList(
            "ch1", "ch1_1", "ch2", "ch4", "ch5", "ch10", "ch12a", "ch12b",
            "ch6a", "ch9", "ch11", "ch15"
    ));

    private static ArrayList<String> messaDomenicale = new ArrayList<>(Arrays.asList(
            "ch1", "ch1_1", "ch2", "ch4", "ch5", "ch10", "ch12a", "ch12b",
            "ch6a", "ch9", "ch11", "ch15",
            "ch6b", "ch3"
    ));

    private static ArrayList<String> messaSolenne = new ArrayList<>(Arrays.asList(
            "ch1", "ch1_1", "ch2", "ch4", "ch5", "ch10", "ch12a", "ch12b",
            "ch6a", "ch9", "ch11", "ch15",
            "ch6b", "ch3",
            "ch6c", "ch7"
    ));

    private static ArrayList<String> viaCrucis = new ArrayList<>(Arrays.asList(
            "ch3", "ch5", "ch7", "ch9"
    ));

    public static ArrayList<Scenario> scenariosArray = new ArrayList<>(Arrays.asList(
            new Scenario("Chiesa aperta", chiesaAperta),
            new Scenario("Messa feriale", messaFeriale),
            new Scenario("Messa domenicale", messaDomenicale),
            new Scenario("Messa solenne", messaSolenne),
            new Scenario("Via Crucis", viaCrucis)));
}
