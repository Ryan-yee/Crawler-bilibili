package com.ryanyee.Crawer_bilibili.conn;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class LiveWSTest {

    @Test
    public void websocketTest() throws InterruptedException {
        int roomID = 9922197;
        int userID = 0;
        String host = "broadcastlv.chat.bilibili.com";
        int port = 443;
        String key = "U5qa6dEuxTUZMvaiiyLI13ydZDqu9rxGtkZgzETTVE8EpantNTqvOAuk49AUHgwa_Qe6WYjzx3okpEV5DX6HIYu1PpGav2MhHmZZW5QgmPRfpHtm6Ti68-545MbELH-vMub5lApl4ZdAgdbl5TkJZ-UdsGxrOOAtsCTInOq5TP41WAbZ";
        liveWS ws = new liveWS(host, port, roomID, userID, key);
        ws.getClient().connect();
        while (true) {

        }
    }

}
