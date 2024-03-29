package org.encinet.oceanbot.common.occommand.commands;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.Adapter;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.common.until.Process;
import org.encinet.oceanbot.common.until.record.BindData;

import java.util.*;
import java.text.SimpleDateFormat;

public class whois extends BasicCommand {
  public whois() {
    super("whois", "查", "[玩家名/QQ]", "查询玩家信息", false);
  }

  @Override
  public void onCommand(BasicSender sender, String label) {
        String[] args = label.split(" ", 2);
        long qq = sender.getQQ();
        if (args.length == 1) {// 无参即查自己
            if (OceanBot.whitelist.contains(qq)) {
                sender.sendMessage(search(OceanBot.whitelist.getBind(qq)));
            } else {
                sender.sendMessage("你还没有绑定游戏ID呢");
            }
        } else {// 有参 支持模糊查询
            StringBuilder sb = new StringBuilder();
            List<BindData> datas = getFromText(args[1]);
            if (datas.size() == 0 ) {
                sb.append("查无此人");
            } else if (datas.size() == 1 ) {
                sb.append(search(datas.get(0)));
            } else if (datas.size() >= 5) {
                sb.append("结果过多，请缩小范围再试吧");
            } else {
                // datas.size() > 1
                sb.append("共找到").append(datas.size()).append("条数据");
                for (BindData data : datas) {
                    sb.append("\n-----").append(search(data));
                }
            }
            sender.sendMessage(sb.toString());
        }
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

        Adapter.Player player = this.adapter.Server.getPlayer(data.uuid());
        if (!player.hasPlayedBefore()) {
            return "此玩家尚未进服";
        } else {
            String f;
            if (player.isOnline()) {
                f = "在线 " + player.ping + "ms";
            } else {
                f = player.isBanned() ? "封禁" : "离线";
            }
            return "ID: " + player.name + " " + f + "\n" +
                    "UUID: " + data.uuid() + "\n" +
                    "QQ: " + data.qq() + "\n" +
                    ((player.firstPlayed != 0L) ? "加入时间: " + dateFormat.format(player.firstPlayed) + "\n" : "") +
                    ((player.lastSeen != 0L) ? "最近游玩: " + dateFormat.format(player.lastSeen): "");
        }
    }
}
