package org.encinet.kitebot.common.command.sender;

import net.minecraft.text.Text;
import net.minecraft.server.command.ServerCommandSource;

import static org.encinet.kitebot.file.Config.prefix;

public class MinecraftSender extends BasicSender {
    final long senderQQ;
    final ServerCommandSource source;
    
    public MinecraftSender(long senderQQ, ServerCommandSource source) {
        this.senderQQ = senderQQ;
        this.source = source;
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
        source.sendMessage(Text.of(message));
    }

    @Override
    public long getQQ() {
        return senderQQ;
    }
}