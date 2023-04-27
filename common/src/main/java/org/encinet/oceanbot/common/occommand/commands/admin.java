package org.encinet.oceanbot.common.occommand.commands;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.mamoe.mirai.contact.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.HttpUnit;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class admin extends BasicCommand {
  public admin() {
    super("admin", "管理", "...", "管理工具", true);
  }

  @Override
  public void onCommand(BasicSender sender, String label) {
    StringBuilder sb = new StringBuilder();
    String[] args = label.split(" ");
        net.mamoe.mirai.contact.Group group = OceanBot.core.getBot().getGroup(Config.MainGroup);
        switch (args[1]) {
            case "check" -> {
                // 云黑名单查询
                sb.append("开始检查");
                ContactList<NormalMember> members = group.getMembers();
                for (NormalMember member : members) {
                    long memberQQ = member.getId();
                    try {
                        String body = HttpUnit.get("https://blacklist.baoziwl.com/api.php?qq=" + memberQQ);
                        JSONObject data = JSON.parseObject(body);
                        int status = data.getInteger("status");
                        if (status == 1) {
                            String reason = data.getString("text_lite");
                            sb.append(memberQQ).append(": ").append(reason).append("\n");
                        }
                    } catch (IOException | IllegalArgumentException | InterruptedException e) {
                        sb.append(memberQQ).append("查询失败\n");
                    }
                }
                sb.append("检查完毕");
            }
            case "bind" -> {
                // .admin bind <uuid> <qq>
                UUID uuid = UUID.fromString(args[2]);
                long qq = Long.parseLong(args[3]);
                
                if (args.length == 4) {
                NormalMember member =
          Objects.requireNonNull(group)
              .getMembers()
              .get(qq);
      if (member == null) {
        sender.sendMessage("在主群中找不到此QQ号");
      }

      OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                
      String nick = member.getNick();
      String playerName = player.getName();
      boolean suc = OceanBot.whitelist.add(uuid, playerName, qq);

      if (!suc) {
        sender.sendMessage("数据库出现异常");
      } else {
        if (!Objects.equals(playerName, nick) && !nick.endsWith("(" + playerName + ")")) {
          member.setNameCard(nick + "(" + playerName + ")");
        }
        sender.sendMessage("绑定成功 " + playerName + "(" + uuid + ")-" + 11);
      }
                
            } else {
                sender.sendMessage("缺少参数");
            }
                }
        }
    sender.sendMessage(sb.toString());
  }
}
