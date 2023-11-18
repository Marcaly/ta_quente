package com.example.taquente;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private EditText editText;
    private OpenWeatherMapService openWeatherMapService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeatherMapService = new OpenWeatherMapService();
        editText = findViewById(R.id.cidade);
        Button umidade = findViewById(R.id.button3);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherData(0);
            }
        });

        umidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData(1);
            }
        });


    }

    private void showPopup(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        dialogBuilder.setView(popupView);

        TextView textViewPopup = popupView.findViewById(R.id.textPopup);
        textViewPopup.setText(message);

        Button buttonClose = popupView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // Fecha o popup quando o botão é clicado
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void getWeatherData(int flag) {
        String city = editText.getText().toString().trim();
        if (!city.isEmpty()) {


            openWeatherMapService.getWeather(city, "0d969099348abac68026bf67a7e0d1d4", new OpenWeatherMapService.WeatherCallback() {
                @Override
                public void onWeatherReceived(WeatherResponse weatherResponse) {
                    double temperaturaAtual = weatherResponse.getMain().getTemp();
                    double umidadeAtual = weatherResponse.getMain().getHumidity();

                    if (flag == 0) {


                        if (temperaturaAtual >= 303.15) {
                            showPopup("Está quente em " + city + String.format("! %nTemperatura: ") + String.format("%.2f", temperaturaAtual - 273.15) + " Celsons");
                        } else if (temperaturaAtual >= 293.15 && temperaturaAtual < 303.15) {
                            showPopup(String.format("Bandido bom é bandido morno! %nTemperatura em ") + city + ": " + String.format("%.2f", temperaturaAtual - 273.15) + " Celsons");
                        } else {
                            showPopup("Está frio em " + city + String.format("! %nTemperatura: ") + String.format("%.2f", temperaturaAtual - 273.15) + " Celsons");
                        }
                    } else if (flag == 1) {

                        if (umidadeAtual <= 12) {
                            showPopup(String.format("Não vai ter educação física! %nÚmidade atual: ") + umidadeAtual + "%");
                        } else if (umidadeAtual > 12 && umidadeAtual <= 40) {
                            showPopup(String.format("Seu nariz está sangrando! %nÚmidade atual: ") + umidadeAtual + "%");
                        } else if (umidadeAtual > 40 && umidadeAtual < 70) {
                            showPopup(String.format("A umidade está ideal! %nÚmidade atual: ") + umidadeAtual + "%");
                        } else {
                            showPopup(city + String.format(" está bem humilde! %nÚmidade atual: ") + umidadeAtual + "%");
                        }
                    }
                }

                @Override
                public void onWeatherError(String errorMessage) {
                }
            });
        }
    }
}