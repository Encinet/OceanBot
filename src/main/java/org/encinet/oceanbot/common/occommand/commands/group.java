package org.encinet.oceanbot.common.occommand.commands;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.mamoe.mirai.contact.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.HttpUnit;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class group extends BasicCommand {
  public group() {
    super("group", new String[] {"g", "群"}, "...", "群管工具", true);
  }

  @Override
  public String onCommand(String label, long qq) {
    StringBuilder sb = new StringBuilder();
    String[] args = label.split(" ");
        if (args.length < 1) {
            return null;
        }
        net.mamoe.mirai.contact.Group group = OceanBot.core.getBot().getGroup(Config.MainGroup);
        switch (args[1]) {
            case "check" -> {
                // 云黑名单查询
                sb.append("开始检查");
                ContactList<NormalMember> members = group.getMembers();
                for (NormalMember member : members) {
                    try {
                    long memberQQ = member.getId();
                    String body = HttpUnit.get("https://blacklist.baoziwl.com/api.php?qq=" + memberQQ);
                    JSONObject data = JSON.parseObject(body);
                    int status = data.getInteger("status");
                    if (status == 1) {
                        String reason = data.getString("text_lite");
                        sb.append(memberQQ).append(": ").append(reason).append("\n");
                    }
                    } catch (IOException | IllegalArgumentException | InterruptedException e) {
                        sb.append(qq).append("查询失败\n");
                    }
                }
                sb.append("检查完毕");
            }
        }
    return sb.toString();
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
}
