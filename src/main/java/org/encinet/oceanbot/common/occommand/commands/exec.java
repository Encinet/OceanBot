package org.encinet.oceanbot.common.occommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.BasicCommand;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.common.occommand.sender.MinecraftSender;
import org.encinet.oceanbot.until.QQCommandSender;
import org.encinet.oceanbot.until.TextUntil;

public class exec extends BasicCommand {

  public exec() {
    super("exec", new String[] {"c", "执行"}, "<命令>", "执行控制台命令", true);
  }

  @Override
  public void onCommand(BasicSender sender, String label) {
    String[] split = label.split(" ", 2);
    if (split.length == 1) {
      sender.sendMessage("无法找到需要执行的命令");
    } else {
      String cmd = split[1];
      QQCommandSender cs = new QQCommandSender(Bukkit.getConsoleSender());
      new BukkitRunnable() {
        @Override
        public void run() {
          Bukkit.dispatchCommand(cs, cmd);
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
          }
          String cmdReturn = cs.get();
          sender.sendMessage(sender instanceof MinecraftSender ? cmdReturn : TextUntil.removeColorCodes(cmdReturn));
        }
      }.runTask(OceanBot.plugin);
      sender.sendMessage("指令发送完成");
    }
  }
}
