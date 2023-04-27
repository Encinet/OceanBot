package org.encinet.kitebot.mirai.event;

import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;

import org.encinet.kitebot.mirai.consciousness.response.Text;
import org.jetbrains.annotations.NotNull;

public class Other extends SimpleListenerHost {
    // 戳一戳
    @EventHandler
    public void onNudge(@NotNull NudgeEvent event) {
        event.getSubject().sendMessage(Text.actCute());
    }
}