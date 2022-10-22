package org.encinet.oceanbot.until;

public class Tool {
    public <T> boolean listCon(List<T> list, T element) {
        for (T n : list) {
            if (Object.equals(n, element)) {
                return true;
            }
        }
        return false;
    }
}