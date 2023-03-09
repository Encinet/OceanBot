package org.encinet.oceanbot.until;

import org.encinet.oceanbot.OceanBot;
import org.encinet.oceanbot.until.HttpUnit;

import java.io.IOException;
import java.util.List;

import static org.encinet.oceanbot.file.Config.*;

/**
 * 违禁关键词检测
 */
public class Recall {
    public static boolean is(String text) {
        try {
            String m = text.toLowerCase().trim();
            m = m.replace("\n", "").replace("\r", "").replace("\t", "").replace(" ", "");
            // List<String> words = Segmenter.segment(m);
            String words = HttpUnit.get("http://api.pullword.com/get.php?source=" + m + "&param1=0.8&param2=0");
            for (String n : recallText) {
                if (words.contains(n)) {
                    return true;
                }
            }
        } catch (IOException | IllegalArgumentException | InterruptedException e) {
            OceanBot.core.getBot().getGroup(LogGroup).sendMessage("Recall Error " + System.currentTimeMillis() + "\n" + e.toString());
        }
        return false;
    }
}
