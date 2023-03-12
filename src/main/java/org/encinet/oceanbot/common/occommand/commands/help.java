package org.encinet.oceanbot.common.occommand.commands;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.OcCommand;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQUntil;

import java.util.*;

public class help extends BasicCommand {
  public help() {
    super("help", "帮助", "[命令头]", "查看机器人命令帮助", false);
  }

  @Override
  public String onCommand(String label, long qq) {
    StringBuilder sb = new StringBuilder();
        
    String[] args = label.split(" ");
    // 第二参数是否存在可用命令头
    BasicCommand choose = args.length == 2 ? OceanBot.occommand.getFromHead(args[1]) : null;
    // 是否拥有权限
    choose = choose != null && QQUntil.canEnter(choose.getAdmin(), qq) ? choose : null;
        
    if (choose == null) {
      List<BasicCommand> commands = OceanBot.occommand.commands;
      sb.append("消息前加#可发送到服务器或QQ群\n");
      sb.append("可用指令前缀 ").append(Config.commandPrefix).append("\n");
      sb.append("-----\n");
      for (BasicCommand command : commands) {
        if (QQUntil.canEnter(command.getAdmin(), qq)) {
        List<String> commandHeads = new ArrayList<>();
        commandHeads.add(command.getHead());
        commandHeads.addAll(Arrays.asList(command.getAlias()));
                    
        // 命令头 包含别名 用,连接 例:help,帮助
        sb.append(String.join(",", commandHeads.toString()));
        // 命令参数 空一格添加在命令头后面
        String commandArgs = command.getArgs();
        if (commandArgs != null) {
          sb.append(" ").append(commandArgs);
        }
        // 命令介绍和命令头参数用" - "来连接
        sb.append(" - ").append(command.getDescription());
        // 如果需要管理权限就打上标记
        sb.append(command.getAdmin() ? "(管理)\n" : "\n");
        }
      }
      sb.append("-----\n");
      sb.append("OceanBot Beta\n");
      sb.append("Made by Encinet");
    } else {
      sb.append("主命令: ").append(choose.getHead()).append("\n");
      sb.append("别名: ").append(Arrays.asList(choose.getAlias())).append("\n");
      String chooseArgs = choose.getArgs();
      if (chooseArgs != null) {
        sb.append("参数: ").append(chooseArgs).append("\n");
      }
      sb.append("介绍: ").append(choose.getDescription());
    }
    return sb.toString();
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
}
