package org.encinet.oceanbot.until;

import java.util.concurrent.ThreadLocalRandom;

public class Verify {
    private static final int extent = 7;

    public static String get() {
        return generate();
    }

    private static String generate() {
        StringBuilder verify = new StringBuilder(extent);
        for (byte i = 0; i < extent; i++) {
            verify.append(ThreadLocalRandom.current().nextInt(9));
        }
        return verify.toString();
    }
}
