package org.encinet.oceanbot.until;

import java.util.Random;

public class Verify {
    private static final Random r = new Random();
    private static final int max = 9999999;
    private static final int min = 1000000;

    public static String get() {
        return (r.nextInt(max - min) + min).toString();
    }
}
