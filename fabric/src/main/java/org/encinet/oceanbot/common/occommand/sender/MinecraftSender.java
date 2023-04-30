package org.encinet.oceanbot.common.command.sender;

import net.minecraft.text.Text;
import net.minecraft.server.command.ServerCommandSource;

import org.encinet.oceanbot.common.occommand.sender.BasicSender;
import org.encinet.oceanbot.FabricBootstrap;

public class MinecraftSender extends BasicSender {
    final long senderQQ;
    final ServerCommandSource source;
    
    public MinecraftSender(long senderQQ, ServerCommandSource source) {
        this.senderQQ = senderQQ;
        this.source = source;
    }
    
    @Override
    public void sendMessage(String message) {
        String prefix = " §6Ocean§fBot §8>> §r";
        if (message.contains("\n")) {
            // 多行
            message = prefix + " --------\n" + message + "\n------------";
        } else {
            // 单行
            message = prefix + message;
        }
        source.sendMessage(Text.of(message));
    }

    @Override
    public long getQQ() {
        return senderQQ;
    }
}