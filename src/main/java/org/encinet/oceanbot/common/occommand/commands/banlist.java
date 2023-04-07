package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;

import java.util.*;
import java.util.stream.Collectors;

public class banlist extends BasicCommand {
  public banlist() {
    super("banlist", new String[] {"bl", "封禁列表"}, "列出封禁玩家", false);
  }

  @Override
  public void onCommand(BasicSender sender, String label) {
    List<String> banedPlayers = new ArrayList<>();
    for (OfflinePlayer n : Bukkit.getServer().getBannedPlayers()) {
      banedPlayers.add(n.getName());
    }
    // 字母顺序
    banedPlayers = banedPlayers.stream().sorted().collect(Collectors.toList());
    // 统计
    int num = banedPlayers.size();

    sender.sendMessage("当前被封禁 " + num + " 人\n" + String.join(", ", banedPlayers));
  }
}
