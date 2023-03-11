package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.event.PlayerNum;
import org.encinet.oceanbot.file.Config;

import java.util.*;
import java.util.stream.Collectors;

public class list extends BasicCommand {
  private static final Random random = new Random();

  public list() {
    super("list", "在线", "列出在线玩家", false);
  }

  @Override
  public String onCommand(String label, long qq) {
    StringBuilder sb = new StringBuilder();
    // 更新群昵称
    PlayerNum.chance();
    // 添加玩家id列表
    List<String> onlinePlayers = new ArrayList<>();
    for (Player n : Bukkit.getServer().getOnlinePlayers()) {
      onlinePlayers.add(n.getName() + (n.isAfk() ? " [AFK]" : ""));
    }
    // 字母顺序
    onlinePlayers = onlinePlayers.stream().sorted().collect(Collectors.toList());
    // 执行
    int num = onlinePlayers.size();
    if (Config.numMessage.containsKey(num)) {
      List<String> messages = Config.numMessage.get(num);
      sb.append(messages.get(random.nextInt(messages.size()))); // 随机使用消息
      for (int i = 0; i < num; i++) {
        replaceAll(sb, "{" + i + "}", onlinePlayers.get(i));
      }
    } else {
      sb.append("当前 ").append(num).append(" 人在线\n").append(String.join("\n", onlinePlayers));
    }
    return sb.toString();
  }

  public static void replaceAll(StringBuilder builder, String from, String to) {
    int index = builder.indexOf(from);
    while (index != -1) {
      builder.replace(index, index + from.length(), to);
      index += to.length(); // Move to the end of the replacement
      index = builder.indexOf(from, index);
    }
  }

  public String onTab(String[] args, long qq) {
    return null;
  }
}
