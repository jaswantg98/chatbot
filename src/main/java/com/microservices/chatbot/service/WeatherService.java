package com.microservices.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    @Value("${rapidapi.weather.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate) {
    }

    public String getWeatherForecast(String city) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=" + city))
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return "Weather data: " + response.body();
        } catch (Exception e) {
            return "Error fetching weather information.";
        }
    }

}
