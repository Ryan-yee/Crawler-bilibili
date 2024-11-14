package com.ryanyee.Crawer_bilibili.conn;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LiveAPITest {

    @Test
    public void fetchRoomDataTest() {
        int roomID = 26966466;
        liveAPI api = new liveAPI();
        assertDoesNotThrow(() -> api.fetchRoomData(roomID));
    }

    @Test
    public void fetchDanmuConfTest() {
        int roomID = 26966466;
        liveAPI api = new liveAPI();
        assertDoesNotThrow(() -> api.fetchDanmuConf(roomID));
    }
}
