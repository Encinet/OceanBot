package org.encinet.oceanbot;

import org.encinet.oceanbot.Config;

import com.lilittlecat.chatgpt.ChatGPT;

public class ChatBot {
  public static ChatGPT chatGPT;
  public static boolean enable = true;// 聊天开关
  
  
  public static String send(String text) {
    if (!enable) {
      return null;
    }
    if (chatGPT == null) {
      chatGPT = new ChatGPT(Config.ChatToken);
    }
    return chatGPT.ask(text);
  }
}