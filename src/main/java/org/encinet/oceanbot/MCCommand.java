package org.encinet.oceanbot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.encinet.oceanbot.execute.Whois;


public class MCCommand implements CommandExecutor {
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || "help".equals(args[0])) {
            sender.sendMessage(prefix + "-----§6Ocean§fBot&r-----");
            sender.sendMessage(prefix + "群消息前加#即可将消息发送至服务器\n服务器消息前加#即可将消息发送至QQ群");
            sender.sendMessage(prefix + "/oc reload - 重载插件");
            sender.sendMessage(prefix + "当前版本:" + Config.ver);
            sender.sendMessage(prefix + "Made By Encinet");
            return true;
        }
        switch (args[0]) {
            case "reload":
                if (sender.hasPermission("oc.admin")) {
                    Config.load();
                } else {
                    sender.sendMessage(prefix + "你没有权限");
                }
                break;
            default:
                sender.sendMessage(prefix + "错误的语法");
                break;
        }
        return true;
    }
}
