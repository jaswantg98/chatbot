package com.microservices.chatbot.controller;

import com.microservices.chatbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ChatbotController {

    private final List<String> chatResponses = new ArrayList<>();
    private final WeatherService weatherService;

    @Autowired
    public ChatbotController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/chatbot")
    public String showChatbotPage(Model model) {
        model.addAttribute("welcomeMessage", "Welcome to the Chatbot Web Application!");
//        model.addAttribute("chatResponses", chatResponses);
        return "chatbot";
    }

    @PostMapping("/chat")
    public String handleUserMessage(String userMessage, Model model) {
        if (userMessage.toLowerCase().contains("weather")) {
            String city = extractCityFromMessage(userMessage);
            String weatherResponse = weatherService.getWeatherForecast("London");
            chatResponses.add("User: " + userMessage);
            chatResponses.add("Chatbot: " + weatherResponse);
        } else {
            String botResponse = processUserMessage(userMessage);
            chatResponses.add("User: " + userMessage);
            chatResponses.add("Chatbot: " + botResponse);
        }
        model.addAttribute("chatResponses", chatResponses);
        return "chatbot";
    }

    //For now I am using just these 3 cities
    private String extractCityFromMessage(String userMessage) {
        Pattern cityPattern = Pattern.compile("\\b(?:London|Jersey City|Michigan)\\b", Pattern.CASE_INSENSITIVE);

        Matcher matcher = cityPattern.matcher(userMessage);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "New York";
        }
    }

    //This method is used if input contains any other data except the valid cities
    private String processUserMessage(String userMessage) {
        return "You said: " + userMessage;
    }
}
