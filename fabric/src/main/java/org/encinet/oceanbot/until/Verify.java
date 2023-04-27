package org.encinet.kitebot.until;

import java.util.Random;

/**
 * 验证码生成
 */
public class Verify {
    private static final Random r = new Random();
    private static final int max = 9999999;
    private static final int min = 1000000;

    public static String get() {
        return Integer.toString(r.nextInt(max - min) + min);
    }
}
