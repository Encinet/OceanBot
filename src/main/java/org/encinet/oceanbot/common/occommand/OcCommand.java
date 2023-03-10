package org.encinet.oceanbot.common.occommand;

import org.encinet.oceanbot.common.occommand.commands.*;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.QQUntil;

import java.io.IOException;
import java.util.*;

public class OcCommand {
  public static List<BasicCommand> commands = new ArrayList<>();

  public OcCommand() {
    register(new exec());
    register(new help());
    register(new info());
    register(new list());
  }

  private void register(BasicCommand command) {
    commands.add(command);
  }

  public String execute(String label, long qq, boolean color) {
    String commandHead = label.split(" ")[0];
    BasicCommand command = getFromHead(commandHead);
    if (command != null && QQUntil.canEnter(command.getAdmin(), qq)) {
      return command.onCommand(label, qq, color);
    } else {
      return null;
    }
  }

  public final BasicCommand getFromHead(String commandHead) {
    for (BasicCommand command : commands) {
      if (commandHead.equals(command.getName())) {
        return command;
      } else {
        for (String alias : command.getAlias())
          if (commandHead.equals(alias)) {
            return command;
          }
      }
    }
    return null;
  }
}
