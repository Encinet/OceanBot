package org.encinet.oceanbot.common.occommand;

import net.mamoe.mirai.internal.network.component.NoSuchComponentException;
import org.encinet.oceanbot.common.Adapter;
import org.encinet.oceanbot.common.occommand.commands.*;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQUntil;
import org.encinet.oceanbot.until.TextUntil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;

public class OcCommand {
  public Adapter adapter;
  public static List<BasicCommand> commands = new ArrayList<>();

  public OcCommand(Adapter adapter) {
    this.adapter = adapter;
        
    // 加载并实例化
    String packageName = "org.encinet.oceanbot.common.occommand.commands";
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String path = packageName.replace('.', '/');
    java.net.URL resource = classLoader.getResource(path);
    if (resource != null) {
      java.io.File dir = new java.io.File(resource.getFile());
      if (dir.exists() && dir.isDirectory()) {
        String[] files = dir.list();
        for (String file : files) {
          if (file.endsWith(".class")) try {
            String className = packageName + '.' + file.substring(0, file.length() - 6);
            Class<?> clazz = Class.forName(className);
            if (BasicCommand.class.isAssignableFrom(clazz)) {
              Constructor<?> constructor = clazz.getDeclaredConstructor();
              constructor.setAccessible(true);
              BasicCommand command = (BasicCommand) constructor.newInstance();
              commands.add(command);
            }
          } catch (Exception e) {
              e.printStackTrace();
              continue;
          }
        }
      }
    }
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
