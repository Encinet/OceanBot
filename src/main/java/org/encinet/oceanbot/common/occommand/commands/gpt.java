package org.encinet.oceanbot.common.occommand.commands;

import com.plexpt.chatgpt.ChatGPT;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.common.occommand.sender.QQFriendSender;
import org.encinet.oceanbot.common.occommand.sender.QQGroupSender;
import org.encinet.oceanbot.file.Config;

import java.util.*;

public class gpt extends BasicCommand {

  ChatGPT chatGPT =
      ChatGPT.builder()
          .apiKeyList(Config.ChatGPT_Tokens)
          .timeout(900)
          // .proxy(proxy)
          .apiHost("https://api.openai.com/") // 代理地址
          .build()
          .init();

  long lastTime = 0;

  public gpt() {
    super("chatgpt", "gpt", "<文本>", "与OpenAI ChatGPT对话", false);
  }

  @Override
  public synchronized void onCommand(BasicSender sender, String label) {
    if (Config.ChatGPT_Enable) {
      String[] split = label.split(" ", 2);
      if (split.length == 1) {
        sender.sendMessage("你似乎什么都没说");
      } else if (isRunning()) {
        sender.sendMessage("已有一个ChatGPT在运行中");
      } else {
        long now = System.currentTimeMillis();
        if (now >= lastTime + 5000) {
          // Start
          MessageReceipt<Group> mr;
          if (sender instanceof QQGroupSender group) {
            mr = group.sendMessageReturnReceipt("发送成功 请耐心等待回复");
          } else {
            mr = null;
            sender.sendMessage("发送成功 请耐心等待回复");
          }
          lastTime = now;
          new Thread(() -> {
                            try {
            sender.sendMessage(chatGPT.chat(split[1]));
                    } catch () {
                        
                    } finally {
            if (mr != null) {
                mr.recall();
            }
                                }
          }, "OceanBot-ChatGPT").start();
        } else {
          sender.sendMessage("全局信息发送过快");
        }
      }
    } else {
      sender.sendMessage("ChatGPT Disable");
    }
  }

  private boolean isRunning() {
    Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
    // 遍历所有线程查找是否有线程名为"OceanBot-ChatGPT"的线程
    for (Thread thread : threadMap.keySet()) {
      if ("OceanBot-ChatGPT".equals(thread.getName())) {
        return true;
      }
    }
    return false;
  }
}
