package org.encinet.oceanbot;

import org.encinet.oceanbot.Config;

import com.lilittlecat.chatgpt.ChatGPT;

public class ChatBot {
  public static ChatGPT chatGPT;
  
  public static String ask(String text) {
    if (chatGPT == null) {
      chatGPT = new ChatGPT(Config.ChatToken);
    }
    return chatGPT.ask(text);
  }
}