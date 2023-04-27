package org.encinet.oceanbot.common.occommand.sender;

import org.bukkit.command.CommandSender;

import static org.encinet.oceanbot.OceanBot.prefix;

public class MinecraftSender extends BasicSender {
    final long senderQQ;
    final CommandSender sender;
    
    public MinecraftSender(long senderQQ, CommandSender sender) {
        this.senderQQ = senderQQ;
        this.sender = sender;
    }
    
    @Override
    public void sendMessage(String message) {
        if (message.contains("\n")) {
            // 多行
            message = prefix + " --------\n" + message + "\n------------";
        } else {
            // 单行
            message = prefix + message;
        }
        sender.sendMessage(message);
    }

    @Override
    public long getQQ() {
        return senderQQ;
    }
}