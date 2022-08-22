package org.encinet.oceanbot;

import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.execute.Function;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MCCommand implements CommandExecutor {
    public static final String prefix = " §6Ocean§fBot §8>> §r";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "你不是玩家");
            return true;
        }
        UUID uuid = Bukkit.getOfflinePlayer(sender.getName()).getUniqueId();
        if (args.length < 1 || "help".equals(args[0])) {
            sender.sendMessage(prefix + Function.on("help", MiraiMC.getBind(uuid)));
        } else {
            StringBuilder c = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                c.append(args[i]);
                if (i < (args.length - 1)) {
                    c.append(" ");
                }
            }
            sender.sendMessage(prefix + Function.on(c.toString(), MiraiMC.getBind(uuid)));
        }
        return true;
    }
}
