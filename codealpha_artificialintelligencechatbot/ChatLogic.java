/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpha_artificialintelligencechatbot;

public class ChatLogic {
    private NLPProcessor nlp;
    private Dataset data;

    public ChatLogic() {
        nlp = new NLPProcessor();
        data = new Dataset();
    }

    public String getResponse(String userInput) {
        String keyword = nlp.extractKeyword(userInput.toLowerCase());
        return data.getAnswer(keyword);
    }
}
