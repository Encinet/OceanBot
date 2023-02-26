package org.encinet.oceanbot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.file.Whitelist;
import org.encinet.oceanbot.execute.Function;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static org.encinet.oceanbot.OceanBot.prefix;

/**
 * 主命令
 */
public class MCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "你不是玩家");
            return true;
        }
        UUID uuid = Bukkit.getOfflinePlayer(sender.getName()).getUniqueId();
        if (args.length < 1 || "help".equals(args[0])) {
            sender.sendMessage(prefix + Function.on("help", Whitelist.getBind(uuid)));
        } else {
            StringBuilder c = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                c.append(args[i]);
                if (i < (args.length - 1)) {
                    c.append(" ");
                }
            }
            sender.sendMessage(prefix + Function.on(c.toString(), Whitelist.getBind(uuid)));
        }
        return true;
    }
}
