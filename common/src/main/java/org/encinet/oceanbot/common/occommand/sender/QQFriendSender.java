package org.encinet.oceanbot.common.occommand.sender;

import net.mamoe.mirai.contact.Friend;

public class QQFriendSender extends BasicSender {
    final Friend friend;
    
    public QQFriendSender(Friend friend) {
        this.friend = friend;
    }
    
    @Override
    public void sendMessage(String message) {
        friend.sendMessage(message);
    }

    @Override
    public long getQQ() {
        return friend.getId();
    }
}