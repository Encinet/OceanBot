package org.encinet.oceanbot.QQ.AI;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.encinet.oceanbot.Config.BotID;

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
     */
    public static void message(long qq, String msg) {
        String trim = msg.trim();
        // 呼唤判断 TODO
        if (inDialogueMode(qq)
            || isMention(trim)
            || ThreadLocalRandom.current().nextInt(1, 101) == ANSWER_PROBABILITY) {
            // 进入对话模式
            long nowTime = System.currentTimeMillis();
            dialogue.put(qq, nowTime);
            // 回复
            answer(trim);
        }
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
        String lower = msg.toLowerCase();
        for (String text : mentions) {
            if (lower.contains(text)) {
                return true;
            }
        }
        return false;
    }

    private static void answer(String msg) {
        new Thread(() -> {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    // 执行请求的URL
                    .uri(URI.create("https://api.pawan.krd/chat/gpt?text=" + msg + "&lang=zh-CN"))
                    // 指定请求超时的时长
                    .timeout(Duration.ofMinutes(1))
                    // 指定请求头
                    .header("Content-Type", "text/html")
                    // 创建GET请求
                    .GET()
                    .build();
            // HttpResponse.BodyHandlers.ofString()指定将服务器响应转化成字符串
            HttpResponse.BodyHandler<String> bh = HttpResponse.BodyHandlers.ofString();
            // 发送请求，获取服务器响应
            try {
                HttpResponse<String> response = client.send(request,bh);
                JSONObject data = JSON.parseObject(response.body());
                data.getString("reply");
            } catch (IOException | InterruptedException e) {
                // 向工作群发送错误信息
            }
        }, "OceanBot-AI").start();
    }
}
