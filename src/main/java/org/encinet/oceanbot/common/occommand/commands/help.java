package org.encinet.oceanbot.common.occommand.commands;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.OcCommand;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQUntil;

import java.util.*;

public class help extends BasicCommand {
  public help() {
    super("help", "帮助", "查看机器人命令帮助", false);
  }

  @Override
  public String onCommand(String label, long qq) {
    StringBuilder sb = new StringBuilder();
    List<BasicCommand> commands = OceanBot.occommand.commands;
    String[] args = label.split(" ");
    BasicCommand choose = args.length == 2 ? OceanBot.occommand.getFromHead(args[1]) : null;
    if (choose == null) {
      sb.append("消息前加#可发送到服务器或QQ群\n");
      sb.append("可用指令前缀 ").append(Config.commandPrefix).append("\n");
      sb.append("-----\n");
      for (BasicCommand command : commands) {
        if (QQUntil.canEnter(super.getAdmin(), qq)) {
        List<String> commandHeads = new ArrayList<>();
        commandHeads.add(command.getHead());
        commandHeads.addAll(Arrays.asList(command.getAlias()));
        sb.append(commandHeads.toString()).append(" - ").append(command.getDescription()).append(super.getAdmin() ? "(仅管理可用)\n" : "\n");
        }
      }
      sb.append("-----\n");
      sb.append("OceanBot Beta\n");
      sb.append("Made by Encinet");
    } else {
      sb.append("主命令: ").append(choose.getHead()).append("\n");
      sb.append("别名: ").append(Arrays.asList(choose.getAlias())).append("\n");
      sb.append("介绍: ").append(choose.getDescription());
    }
    return sb.toString();
  }

  public String onTab(String[] args, long qq) {
    return null;
  }
}
