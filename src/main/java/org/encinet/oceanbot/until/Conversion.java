package org.encinet.oceanbot.until;

import java.text.DecimalFormat;

public class Conversion {
    static final int tickSecond = 20;
    static final int second = 60;
    static final int minute = 60;
    static final int hour = 24;
    static final int day = 24;
    static DecimalFormat df = new DecimalFormat("0.01");

    public static String ticksToText(int ticks) {
        if (ticks <= (second * tickSecond)) {
            return (ticks / tickSecond) + "秒";
        } else if (ticks <= (second * minute * tickSecond))  {
            return (ticks / tickSecond / second) + "分";
        } else if (ticks <= (second * minute * hour * tickSecond))  {
            return (ticks / tickSecond / second / minute) + "时";
        } else {
            return (ticks / tickSecond / second / minute / day) + "天";
        }
    }
}
