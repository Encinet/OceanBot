package org.encinet.oceanbot.QQ.AI.Consciousness;

import org.encinet.oceanbot.OceanBot;

import java.io.File;

public class Audio {
    public static File get(String text) {
        if (text.equals("哼" + repeat("啊", text.length() - 1))) return OceanBot.getFile("files/YeShouXianBei.m4a");
        if (text.contains("奥力给") || text.contains("奥利给") || text.contains("奥里给")) return OceanBot.getFile("files/AoLiGei.mp3");
        return null;
    }

    /**
     * 将某段文本重复
     * @param text 原始文本
     * @param count 重复次数
     * @return 重复后
     */
    private static String repeat(String text, int count) {
        return String.valueOf(text).repeat(Math.max(0, count));
    }
}
