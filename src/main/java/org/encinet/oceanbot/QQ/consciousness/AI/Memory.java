package org.encinet.oceanbot.QQ.consciousness.AI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记忆对话
 * 为了不让机器人忘记刚刚说了什么
 */
public class Memory {
    static Map<Long, StringBuilder> texts = new ConcurrentHashMap<>();
    public static StringBuilder getStringBuilder(long qq) {
        return texts.containsKey(qq) ?
                texts.get(qq) : texts.put(qq, new StringBuilder());
    }
    public static String getString(long qq) {
        StringBuilder msg = texts.get(qq);
        return msg == null ? null : msg.toString();
    }

    public static void addUserMessage(long qq, String msg) {
        getStringBuilder(qq).append("问:").append(msg).append('\n');
    }
    public static void addBotMessage(long qq, String msg) {
        getStringBuilder(qq).append("答:").append(msg).append('\n');
    }
}
