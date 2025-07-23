/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpha_artificialintelligencechatbot;

public class NLPProcessor {
    public String extractKeyword(String text) {
        if (text.contains("hello") || text.contains("hi")) return "greeting";
        if (text.contains("bye") || text.contains("goodbye")) return "farewell";
        if (text.contains("name")) return "name";
        if (text.contains("help") || text.contains("support")) return "help";
        if (text.contains("java")) return "java";
        if (text.contains("project")) return "project";
        if (text.contains("thanks") || text.contains("thank")) return "thanks";
        return "default";
    }
}
