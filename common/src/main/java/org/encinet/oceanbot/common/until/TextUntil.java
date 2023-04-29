package org.encinet.oceanbot.common.until;

import java.util.*;

public class TextUntil {
    static char[] color = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'R', 'r', 'X', 'x'};
    public static String removeColorCodes(String str) {
        for (char c : color) {
            String a = "§" + c;
            String b = "&" + c;
            str = str.replace(a, "");
            str = str.replace(b, "");
        }
        return str;
    }
}