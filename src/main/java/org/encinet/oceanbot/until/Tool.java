package org.encinet.oceanbot.until;

public class Tool {
    public <T> boolean listCon(List<T> list, T element) {
        for (T n : list) {
            if (n.equals(element)) {
                return true;
            }
        }
        return false;
    }
}