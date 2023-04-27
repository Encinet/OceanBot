package org.encinet.kitebot.until;

import org.encinet.kitebot.KiteBot;
import org.encinet.kitebot.file.Config;
import org.encinet.kitebot.until.HttpUnit;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class QQUntil {
  /**
   * 违禁词检测
   *
   * @param text 文本
   * @return true 为检测到违禁词
   */
  public static boolean shouldRecall(String text) {
    try {
      String m = URLEncoder.encode(text.trim());
      // 分词api
      String words =
          HttpUnit.get("http://api.pullword.com/get.php?source=" + m + "&param1=0.8&param2=0").toLowerCase();
      for (String n : Config.recallText) {
        if (words.contains(n)) {
          return true;
        }
      }
    } catch (IOException | InterruptedException e) {
      KiteBot.core
          .getBot()
          .getGroup(Config.LogGroup)
          .sendMessage("Recall Error " + System.currentTimeMillis() + "\n" + e.toString());
    }
    return false;
  }

  /**
   * 管理员判断
   *
   * @param qq QQ号
   * @return true 为是管理员
   */
  public static boolean isAdmin(long qq) {
    for (Long now : Config.admin) {
      if (now.equals(qq)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断是否可以继续执行 需要验证管理 且 qq不是管理 阻止 需要验证管理 且 qq是管理 允许 不需要验证管理 允许
   *
   * @param shouldAdmin 是否需要验证管理员
   * @param qq QQ号
   * @return true 可以执行
   */
  public static boolean canEnter(boolean shouldAdmin, long qq) {
    if (shouldAdmin) {
      if (isAdmin(qq)) {
        return true;
      } else return false;
    } else {
      return true;
    }
  }
}
