package org.encinet.oceanbot.common.until.record;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * 玩家记录时间存储
 * 目前仅用于绑定检测是否超时功能
 */
public class Data {
    private final UUID uuid;// 添加时间
    private final String name;// 玩家名
    private final Long time;// 记录时间

    //构造方法
    public Data(UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
        this.time = System.currentTimeMillis();
    }

    public UUID getUUID() {
        return this.uuid;
    }
    public String getName() {
        return this.name;
    }
    public Long getTime() {
        return time;
    }
}

