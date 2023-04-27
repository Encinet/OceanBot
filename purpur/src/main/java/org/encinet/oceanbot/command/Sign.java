package org.encinet.oceanbot.command;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.until.Money;
import org.encinet.oceanbot.until.record.BindData;
import org.jetbrains.annotations.NotNull;

import static org.encinet.oceanbot.OceanBot.prefix;

/**
 * 签到
 */
public class Sign implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            BindData data = OceanBot.whitelist.getBind(player.getUniqueId());
            if (data == null) {
                sender.sendMessage(prefix + "数据库异常，请联系管理员");
                return true;
            } else {
                long qqNum = data.qq();
            if (Money.qq.contains(qqNum)) {
                sender.sendMessage(prefix + "你已经签到过了");
            } else {
                int money = (args.length > 1) ? Money.get(args[1]) : Money.get();
                String moneyText = (money >= 0 ? "获得" + money : "丢失" + (-money)) + "米币";
                EconomyResponse r = Money.change(player, money);
                if (r.transactionSuccess()) {
                    Money.qq.add(qqNum);
                    sender.sendMessage(prefix + "签到成功 " + moneyText);
                } else {
                    sender.sendMessage(prefix + "签到功能出错 请反馈管理 %s", r.errorMessage);
                }
            }
                }
        }
        return true;
    }
}
