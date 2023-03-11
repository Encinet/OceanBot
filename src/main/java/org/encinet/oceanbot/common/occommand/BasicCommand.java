package org.encinet.oceanbot.common.occommand;

public abstract class BasicCommand {
  // 命令头
  final String head;
  // 命令头别名
  final String[] alias;
  // 介绍
  final String description;
  // 是否需要管理员权限
  final boolean admin;

  public BasicCommand(String head, String[] alias, String description, boolean admin) {
    this.head = head;
    this.alias = alias;
    this.description = description;
    this.admin = admin;
  }

  public BasicCommand(String head, String alias, String description, boolean admin) {
    this(head, new String[] {alias}, description, admin);
  }

  public abstract String onCommand(String label, long qq);

  public abstract String onTab(String[] args, long qq);

  public final String getHead() {
    return this.head;
  }

  public final String[] getAlias() {
    return this.alias;
  }

  public final String getDescription() {
    return this.description;
  }

  public final boolean getAdmin() {
    return this.admin;
  }
}
