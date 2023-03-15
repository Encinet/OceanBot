package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.until.Process;
import org.encinet.oceanbot.until.record.BindData;

import java.util.*;
import java.text.SimpleDateFormat;

public class whois extends BasicCommand {
  public whois() {
    super("whois", "查", "[玩家名/QQ]", "查询玩家信息", false);
  }

  @Override
  public String onCommand(String label, long qq) {
        String[] args = label.split(" ", 2);
        // TODO 模糊查询
        if (args.length == 1) {// 无参即查自己
            if (OceanBot.whitelist.contains(qq)) {
                return search(OceanBot.whitelist.getBind(qq));
            } else {
                return "你还没有绑定游戏ID呢";
            }
        } else {// 有参 支持模糊查询
            StringBuilder sb = new StringBuilder();
            List<BindData> datas = getFromText(args[1]);
            if (datas.size() == 0 ) {
                sb.append("查无此人");
            } else if (datas.size() == 1 ) {
                sb.append(search(datas.get(0)));
            } else {
                // datas.size() > 1
                sb.append("共找到").append(datas.size()).append("条数据\n-----");
                for (BindData data : datas) {
                    sb.append(search(data));
                }
            }
            return sb.toString();
        }
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
    
    private List<BindData> getFromText(String text) {
        try {
            List<BindData> list = new ArrayList<>();
            if (text.startsWith("@")) {
                // @123
                long num = Long.parseLong(text.substring(1));
                list.add(OceanBot.whitelist.getBind(num));
            } else if (OceanBot.whitelist.contains(Long.parseLong(text))) {
                // 123
                list.add(OceanBot.whitelist.getBind(Long.parseLong(text)));
            } else if (text.startsWith("[mirai:at:") && text.endsWith("]")) {
                // [mirai:at:123]
                long num = Long.parseLong(text.substring(9, text.length() - 1));
                list.add(OceanBot.whitelist.getBind(num));
            }
            return list;
        } catch (NumberFormatException e) {
            // 尝试为游戏ID 模糊查询
            return OceanBot.whitelist.getBindFuzzy(text);
        }
    }
    
  private String search(BindData data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OfflinePlayer player = Bukkit.getOfflinePlayer(data.uuid());
        boolean online = player.isOnline();
        if (!player.hasPlayedBefore()) {
            return "此玩家尚未进服";
        } else {
            String f;
            if (online) {
                Player o = Bukkit.getPlayer(data.uuid());
                assert o != null;
                f = "在线 " + o.getPing() + "ms";
            } else {
                f = player.isBanned() ? "封禁" : "离线";
            }
            return "ID: " + player.getName() + " " + f + "\n" +
                    "UUID: " + data.uuid() + "\n" +
                    "QQ: " + data.qq() + "\n" +
                    "经济: " + OceanBot.econ.getBalance(player) + "米币\n" +
                    "加入时间: " + dateFormat.format(player.getFirstPlayed()) + "\n" +
                    "最近游玩: " + dateFormat.format(player.getLastSeen());
        }
    }
}
