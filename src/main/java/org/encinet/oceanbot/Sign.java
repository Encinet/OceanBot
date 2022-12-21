package org.encinet.oceanbot;

import me.dreamvoid.miraimc.api.MiraiMC;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.encinet.oceanbot.until.Money;
import org.jetbrains.annotations.NotNull;

import static org.encinet.oceanbot.OceanBot.prefix;

public class Sign implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            long qqNum = MiraiMC.getBind(player.getUniqueId());
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
        return true;
    }
}
