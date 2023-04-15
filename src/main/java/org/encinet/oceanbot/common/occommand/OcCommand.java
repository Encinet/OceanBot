package org.encinet.oceanbot.common.occommand;

import org.encinet.oceanbot.common.occommand.commands.*;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQUntil;
import org.encinet.oceanbot.until.TextUntil;

import java.io.IOException;
import java.util.*;

public class OcCommand {
  public static List<BasicCommand> commands = new ArrayList<>();

  public OcCommand() {
    register(new admin());
    register(new banlist());
    register(new bind());
    register(new exec());
    register(new gpt());
    register(new help());
    register(new info());
    register(new list());
    register(new reload());
    register(new whois());
  }

  private void register(BasicCommand command) {
    commands.add(command);
  }

  /*
   * 执行命令
   *
   * @param label 命令文本 形如coin 7 true
   * @param qq 执行者QQ号
   * @param color 是否显示颜色 一般mc显示 qq不显示
   * @return 命令输出
   */
  public void execute(BasicSender sender, String label) {
    String commandHead = label.split(" ", 2)[0];
    long qq = sender.getQQ();
    BasicCommand command = getFromHead(commandHead);
    if (command != null && QQUntil.canEnter(command.getAdmin(), qq)) {
      command.onCommand(sender, label);
    }
  }

  /**
   * 通过命令头获取BasicCommand
   *
   * @param commandHead 命令头(文本)
   * @return 找到就返回 找不到就null
   */
  public final BasicCommand getFromHead(String commandHead) {
    for (BasicCommand command : commands) {
      if (commandHead.equals(command.getHead())) {
        // 命令头
        return command;
      } else {
        // 命令头别名
        for (String alias : command.getAlias())
          if (commandHead.equals(alias)) {
            return command;
          }
      }
    }
    return null;
  }
}
