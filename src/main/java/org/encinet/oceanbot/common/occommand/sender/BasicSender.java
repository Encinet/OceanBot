package org.encinet.oceanbot.common.occommand.sender;

// 适配器思想 感谢ChatGPT XD
public abstract class BasicSender {
    public abstract void sendMessage(String message);

    public long getQQ() {
        return 0;
    }
}
