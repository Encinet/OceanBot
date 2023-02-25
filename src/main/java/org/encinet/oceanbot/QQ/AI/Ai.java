package org.encinet.oceanbot.QQ.AI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.encinet.oceanbot.Config.*;

public class Ai {
    // the key is qq number, value is System.currentTimeMillis()
    static Map<Long, Long> dialogue = new ConcurrentHashMap<>();
    // 提及文本
    static final String[] mentions = {"机器人", String.valueOf(BotID), "@" + BotID, "oceanbot", "ocean bot"};
    // 等待用户回复时间 (millis)
    static final long WAITING_TIME = 30000;
    // 回复的概率 (1-100)
    static final long ANSWER_PROBABILITY = 20;

    /**
     * 消息从这里进入
     *
     * @param qq  qq号
     * @param msg 消息
     * @return 回复的信息
     */
    public static String message(long qq, String msg) {
        // 呼唤判断 TODO
        if (inDialogueMode(qq)
            || isMention(msg)
            || ThreadLocalRandom.current().nextInt(1, 101) == ANSWER_PROBABILITY) {
            // 处理回复
            // 进入对话模式
            long nowTime = System.currentTimeMillis();
            dialogue.put(qq, nowTime);
            // 获取信息
            return "awa?";
        } else return null;
    }

    /**
     * 判断这个用户是否处在与机器人的对话模式中
     *
     * @param qq 用户的qq号
     * @return 是否处在与机器人的对话模式中
     */
    private static boolean inDialogueMode(long qq) {
        if (dialogue.containsKey(qq)) {
            long waitTimeLimit = dialogue.get(qq) + WAITING_TIME;
            if (waitTimeLimit > System.currentTimeMillis()) {
                dialogue.remove(qq);
                return false;
            } else return true;
        } else return false;
    }

    /**
     * 是否在聊天中提及(呼唤/@)了机器人
     * @param msg 消息
     * @return 是否提及
     */
    private static boolean isMention(String msg) {
        String lower = msg.trim().toLowerCase();
        for (String text : mentions) {
            if (msg.contains(lower)) {
                return true;
            }
        }
        return false;
    }
}
