package org.encinet.oceanbot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 维护模式
 */
public class Maintenance implements CommandExecutor {
    public static boolean enable = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && !player.hasPermission("oc.admin")) {
            sender.sendMessage("没有权限");
            return true;
        } else {
            enable = !enable;
            sender.sendMessage("维护模式" + (Maintenance.enable ? "启用" : "禁用"));
        }
        return true;
    }
}
