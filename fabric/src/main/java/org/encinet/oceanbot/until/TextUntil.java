package org.encinet.kitebot.until;

import java.util.*;

public class TextUntil {
    static final char[] COLOR_CODES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'R', 'r', 'X', 'x'};
    public static String removeColorCodes(String str) {
        for (char c : COLOR_CODES) {
            String a = "§" + c;
            String b = "&" + c;
            str = str.replace(a, "");
            str = str.replace(b, "");
        }
        return str;
    }
}