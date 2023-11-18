package com.example.taquente;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String main;

    @SerializedName("description")
    private String description;

    // Adicione mais campos conforme necessário

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    // Adicione mais métodos getter conforme necessário
}
