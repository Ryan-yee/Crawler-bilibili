package com.ryanyee.Crawer_bilibili.protocol;

import lombok.Getter;

@Getter
public enum PacketType {
    UNKNOWN(0),
    HEARTBEAT(2),
    POPULARITY(3),
    COMMAND(5),
    ENTER_ROOM(7),
    ENTER_ROOM_RESPONSE(8);

    private final int value;

    PacketType(int value) {
        this.value = value;
    }

    public static PacketType getPacketType(int n) {
        for (PacketType type : PacketType.values()) {
            if (type.getValue() == n) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
