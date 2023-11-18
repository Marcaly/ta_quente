package com.example.taquente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private OpenWeatherMapApi openWeatherMapApi;

    public OpenWeatherMapService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherMapApi = retrofit.create(OpenWeatherMapApi.class);
    }

    public void getWeather(String city, String apiKey, final WeatherCallback callback) {
        Call<WeatherResponse> call = openWeatherMapApi.getWeather(city, apiKey);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        callback.onWeatherReceived(weatherResponse);
                    } else {
                        callback.onWeatherError("Resposta nula");
                    }
                } else {
                    callback.onWeatherError("Erro na resposta da API");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onWeatherError("Falha na chamada da API: " + t.getMessage());
            }
        });
    }

    public interface WeatherCallback {
        void onWeatherReceived(WeatherResponse weatherResponse);

        void onWeatherError(String errorMessage);
    }
}