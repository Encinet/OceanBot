package org.encinet.oceanbot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.file.Config;
import org.encinet.oceanbot.until.record.BindData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static org.encinet.oceanbot.OceanBot.prefix;

/**
 * 主命令
 */
public class MCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        long qq;
        if (sender instanceof Player) {
            UUID uuid = Bukkit.getOfflinePlayer(sender.getName()).getUniqueId();
            BindData data = OceanBot.whitelist.getBind(uuid);
            if (data == null) {
                sender.sendMessage(prefix + "数据库异常，请联系管理员");
                return true;
            } else {
                qq = data.qq();
            }
        } else {
            // 不是玩家就从管理员表中获取一个
            qq = Config.admin.get(0);
        }
        
        if (args.length < 1) {
            // 没参数就返回帮助
            sender.sendMessage(prefix + OceanBot.occommand.execute("help", qq, true));
        } else {
            StringBuilder c = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                c.append(args[i]);
                if (i < (args.length - 1)) {
                    c.append(" ");
                }
            }
            String rt = OceanBot.occommand.execute(c.toString(), qq, true);
            if (rt.contains("\n")) {
                // 多行
                rt = prefix + " --------\n" + rt + "\n------------";
            } else {
                // 单行
                rt = prefix + rt;
            }
            sender.sendMessage(rt);
        }
        return true;
    }
}
