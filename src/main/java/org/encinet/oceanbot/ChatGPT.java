package org.encinet.oceanbot;

import org.encinet.oceanbot.Config;
import com.github.plexpt.chatgpt.Chatbot;

import java.util.Map;

public class ChatGPT {
  public static Chatbot chatbot;
  public static boolean enable = true;// 聊天开关
  
  public static String send(String text) {
    if (!enable) {
      return null;
    }
    if (chatbot == null) {
      reload();
    }
    Map<String, Object> chatResponse = chatbot.getChatResponse(text);
    return chatResponse.get("message").toString();
  }
  
  public static void reload() {
    chatbot = new Chatbot(Config.ChatToken);
  }
}