package com.example.taquente;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temp;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("pressure")
    private double pressure;

    // Adicione mais campos conforme necessário

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }
    public double getPressure() {
        return pressure;
    }

    // Adicione mais métodos getter conforme necessário
}
