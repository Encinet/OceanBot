package org.encinet.oceanbot.common.occommand.commands;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;

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
            long senderQQ = sender.getQQ();
      if (split.length == 1) {
        sender.sendMessage("你似乎什么都没说");
      } else if (isRunning(senderQQ)) {
        sender.sendMessage("已有一个ChatGPT在运行中或全局ChatGPT大于5");
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
            sender.sendMessage(chat(split[1]));
                    } catch (RuntimeException e) {
                        sender.sendMessage("出现异常 请稍后再试");
                        e.getStackTrace();
                    } finally {
            if (mr != null) {
                mr.recall();
            }
                                }
          }, "OceanBot-ChatGPT-" + senderQQ).start();
        } else {
          sender.sendMessage("全局信息发送过快");
        }
      }
    } else {
      sender.sendMessage("ChatGPT Disable");
    }
  }

  private boolean isRunning(long qq) {
        int num = 0;
        String head = "OceanBot-ChatGPT-";
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
    // 遍历所有线程查找是否有线程名为"OceanBot-ChatGPT"的线程
        for (Thread thread : threadMap.keySet()) {
            String name = thread.getName();
            if (name.startsWith(head)) {
                num++;
                if (num > 5) {
                    return true;
                }
                if ((head + qq).equals(name)) {
                    return true;
                }
            }
        }
    return false;
  }
    
    private String chat(String message) {
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(Message.of(message)))
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
