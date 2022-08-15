package org.encinet.oceanbot.until;

import java.util.UUID;

public class Data {
    private final UUID uuid;// 添加时间
    private final Long time;// 记录时间

    //构造方法
    public Data(UUID uuid) {
        this.uuid = uuid;
        this.time = System.currentTimeMillis();
    }

    public UUID getUUID() {
        return this.uuid;
    }
    public Long getTime() {
        return time;
    }
}

