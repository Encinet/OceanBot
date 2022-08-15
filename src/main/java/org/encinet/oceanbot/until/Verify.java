package org.encinet.oceanbot.until;

import java.util.concurrent.ThreadLocalRandom;

public class Verify {
    private static StringBuilder verify;
    private static final int extent = 7;

    /**
     * 密码中的所有类型
     */
    private static final String[] TYPE = {
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "abcdefghijklmnopqrstuvwxyz",
            "0123456789"
    };

    public static String get() {
        return generate();
    }

    private static String generate() {
        verify = new StringBuilder(extent);
        for (byte i = 0; i < extent; i++) {
            g(ThreadLocalRandom.current().nextInt(3));
        }
        return verify.toString();
    }

    private static void g(int options) {
        int tlength = switch (options) {
            case 0, 1 -> 26;
            case 2 -> 10;
            default -> 0;
        };
        int randomInt = ThreadLocalRandom.current().nextInt(tlength);
        verify.append(TYPE[options].charAt(randomInt));
    }
}
