package org.encinet.oceanbot.QQ.AI.Consciousness;

import org.encinet.oceanbot.command.Maintenance;

import java.util.concurrent.ThreadLocalRandom;

import static org.encinet.oceanbot.Config.BotID;

public class Text {
    private static final String at = "[mirai:at:" + BotID.toString() + "]";

    // 以下是检测类
    private static final String[] Interrogative = {"?", "？", "如何", "怎么", "为什么", "为啥"};
    private static final String[] Repetition = {"awa", "qwq", "好耶", "离谱"};
    private static final String[] sigh = {"!", "！", "啊", "哇"};
    // 以下是回复类
    private static final String[] aTed = {"?", "啊?", "ヾ(≧▽≦*)o", "(❁´◡`❁)", "(*/ω＼*)", "(oﾟvﾟ)ノ", "ヾ(^▽^*)))", "(。・ω・。)"};

    public static String get(String text) {
        String trim = text.trim();
        // at
        if (trim.contains(at)) return randomArray(aTed);
        // 疑问词检测
        if (arrayContains(Interrogative, trim)) return query(trim);
        // 复读 去除感叹词检测
        if (arrayEquals(Repetition, randomSigh(trim))) return trim;

        if (trim.contains("我是") && (trim.contains("傻逼") || trim.contains("煞笔"))) {
            return "我也是!";
        }
        return null;
    }

    /**
     * 文本里是否有包含数组内某一个
     *
     * @param array 数组
     * @param text  文本
     * @return 文本里是否有包含数组内某一个
     */
    private static boolean arrayContains(String[] array, String text) {
        for (String s : array) {
            if (text.contains(s)) return true;
        }
        return false;
    }

    /**
     * 文本等于数组内某一个
     *
     * @param array 数组
     * @param text  文本
     * @return 文本等于数组内某一个
     */
    private static boolean arrayEquals(String[] array, String text) {
        for (String s : array) {
            if (text.equals(s)) return true;
        }
        return false;
    }

    /**
     * 去除感叹词
     *
     * @param text 原始文本
     * @return 处理后
     */
    private static String randomSigh(String text) {
        for (String s : sigh) {
            text = text.replaceAll(s, "");
        }
        return text;
    }

    /**
     * 随机从数组中获得文本
     *
     * @param array 数组
     * @return 文本
     */
    private static String randomArray(String[] array) {
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

    /**
     * 疑问信息处理
     *
     * @param text 文本
     * @return 消息 无为null
     */
    private static String query(String text) {
        // 服务器维护
        if (Maintenance.enable) {
            if (text.contains("进不去") || text.contains("人呢") || text.contains("没人")) {
                return "服务器维护中哦";
            }
        }
        // 一般
        if (text.contains("白名单")) {
            return "看公告啊魂淡";
        }
        return null;
    }
}
