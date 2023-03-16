package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQCommandSender;
import org.encinet.oceanbot.until.TextUntil;

import java.util.*;
import java.util.stream.Collectors;

public class exec extends BasicCommand {

  public exec() {
    super("exec", new String[] {"c", "执行"}, "<命令>", "执行控制台命令", true);
  }

  @Override
  public String onCommand(String label, long qq) {
    String[] split = label.split(" ", 2);
    if (split.length == 1) {
      return "无法找到需要执行的命令";
    } else {
      String cmd = split[1];
      QQCommandSender sender = new QQCommandSender(Bukkit.getConsoleSender());
      new BukkitRunnable() {
        @Override
        public void run() {
          Bukkit.dispatchCommand(sender, cmd);
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
          }
          OceanBot.core
              .getBot()
              .getGroup(Config.LogGroup)
              .sendMessage(
                  "来自"
                      + qq
                      + "的命令 "
                      + cmd
                      + "\n---\n"
                      + TextUntil.removeColorCodes(sender.get()));
        }
      }.runTask(OceanBot.plugin);
      return "指令发送完成";
    }
  }

  @Override
  public String onTab(String[] args, long qq) {
    return null;
  }
}
