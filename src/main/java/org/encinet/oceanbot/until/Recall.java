package org.encinet.oceanbot.until;

import io.github.yizhiru.thulac4j.Segmenter;
import org.encinet.oceanbot.Config;

import java.util.List;

/**
 * 违禁关键词检测
 */
public class Recall {
    public static boolean is(String text) {
        String m = text.toLowerCase();
        List<String> words = Segmenter.segment(m);
        for (String n : Config.recallText) {
            if (words.contains(n)) {
                return true;
            }
        }
        return false;
    }
}
