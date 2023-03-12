package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;

import java.util.*;
import java.util.stream.Collectors;

public class banlist extends BasicCommand {
  public banlist() {
    super("banlist", new String[] {"bl", "封禁列表"}, "列出封禁玩家", false);
  }

  @Override
  public String onCommand(String label, long qq) {
    List<String> banedPlayers = new ArrayList<>();
    for (OfflinePlayer n : Bukkit.getServer().getBannedPlayers()) {
      banedPlayers.add(n.getName());
    }
    // 字母顺序
    banedPlayers = banedPlayers.stream().sorted().collect(Collectors.toList());
    // 统计
    int num = banedPlayers.size();

    return "当前被封禁 " + num + " 人\n" + String.join(", ", banedPlayers);
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
}
