package org.encinet.oceanbot.common.occommand.sender;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

public class QQGroupSender extends BasicSender {
    final long senderQQ;
    final Group group;
    final MessageChain messageChain;
    
    public QQGroupSender(long senderQQ, Group group, MessageChain messageChain) {
        this.senderQQ = senderQQ;
        this.group = group;
        this.messageChain = messageChain;
    }
    
    @Override
    public void sendMessage(String message) {
        group.sendMessage(new MessageChainBuilder()
                                .append(new QuoteReply(messageChain))
                                .append(message)
                                .build());
    }

    public MessageReceipt<Group> sendMessageReturnReceipt(String message) {
        return group.sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(messageChain))
                .append(message)
                .build());
    }

    @Override
    public long getQQ() {
        return senderQQ;
    }
}