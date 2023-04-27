package org.encinet.oceanbot.common.occommand.commands;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.common.occommand.BasicCommand;

public class reload extends BasicCommand {

  public reload() {
    super("reload", "重载", "重载插件文件", true);
  }

  @Override
  public void onCommand(BasicSender sender, String label) {
    OceanBot.loadPluginFiles();
    OceanBot.basicLoad();
    sender.sendMessage("重载完毕");
  }
}
