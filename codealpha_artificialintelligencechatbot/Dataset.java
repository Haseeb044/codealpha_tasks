/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpha_artificialintelligencechatbot;

import java.util.HashMap;

public class Dataset {
    private HashMap<String, String> responses;

    public Dataset() {
        responses = new HashMap<>();
        responses.put("greeting", "Hello! How can I assist you today?");
        responses.put("farewell", "Goodbye! Have a great day!");
        responses.put("name", "I am your Java AI Chatbot.");
        responses.put("help", "Sure! I'm here to help you. Ask me anything.");
        responses.put("java", "Java is a powerful OOP language widely used in web and desktop apps.");
        responses.put("project", "This is an AI chatbot using simple NLP and rule-based logic.");
        responses.put("thanks", "You're welcome!");
        responses.put("default", "I'm not sure I understand. Can you please rephrase?");
    }

    public String getAnswer(String keyword) {
        return responses.getOrDefault(keyword, responses.get("default"));
    }
}
