package org.encinet.oceanbot.common.occommand;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.common.Adapter;
import org.encinet.oceanbot.common.occommand.sender.BasicSender;

import java.util.List;

public abstract class BasicCommand {
  public final Adapter adapter = OceanBot.occommand.adapter;
  // 命令头
  final String head;
  // 命令头别名
  final String[] alias;
  // 参数
  final String args;
  // 介绍
  final String description;
  // 是否需要管理员权限
  final boolean admin;

  public BasicCommand(String head, String[] alias, String args, String description, boolean admin) {
    this.head = head;
    this.alias = alias;
    this.args = args;
    this.description = description;
    this.admin = admin;
  }
  public BasicCommand(String head, String alias, String args, String description, boolean admin) {
    this(head, new String[] {alias}, args, description, admin);
  }
  public BasicCommand(String head, String[] alias, String description, boolean admin) {
    this.head = head;
    this.alias = alias;
    this.args = null;
    this.description = description;
    this.admin = admin;
  }
  public BasicCommand(String head, String alias, String description, boolean admin) {
    this(head, new String[] {alias}, description, admin);
  }

  public abstract void onCommand(BasicSender sender, String label);

  public List<String> onTab(String[] args, long qq) {
    return null;
  }

  public final String getHead() {
    return this.head;
  }

  public final String[] getAlias() {
    return this.alias;
  }

  public final String getArgs() {
    return this.args;
  }
    
  public final String getDescription() {
    return this.description;
  }

  public final boolean getAdmin() {
    return this.admin;
  }
}
