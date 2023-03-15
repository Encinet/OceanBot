package org.encinet.oceanbot.common.occommand.commands;

import net.mamoe.mirai.contact.NormalMember;
import org.bukkit.Bukkit;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.until.record.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class bind extends BasicCommand {
  public static final Map<String, Data> codes = new HashMap<>();

  public bind() {
    super("bind", "绑定", "<验证码>", "绑定账号", false);
  }

  @Override
  public String onCommand(String label, long qq) {
    String[] args = label.split(" ", 2);
    if (args.length < 2) {
      return "你还没有输入验证码";
    } else if (!codes.containsKey(args[1])) {
      return "无效验证码";
    } else {
      String code = args[1];
      Data data = codes.get(code);
      codes.remove(code);

      if (OceanBot.whitelist.contains(qq)) {
        return "你已经绑定过了";
      } else if ((System.currentTimeMillis() - data.getTime()) >= 600000) { // 10分钟
        return "验证码已过期";
      }

      NormalMember member =
          Objects.requireNonNull(OceanBot.core.getBot().getGroup(Config.MainGroup))
              .getMembers()
              .get(qq);
      assert member != null;

      String nick = member.getNick();
      String playerName = data.getName();
      boolean suc = OceanBot.whitelist.add(data.getUUID(), playerName, qq);

      if (!suc) {
        return "数据库出现异常，请联系管理员或稍后再试";
      } else {
        if (!Objects.equals(playerName, nick) && !nick.endsWith("(" + playerName + ")")) {
          member.setNameCard(nick + "(" + playerName + ")");
        }
        return "绑定成功, 你现在可以进服游玩啦";
      }
    }
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
}
