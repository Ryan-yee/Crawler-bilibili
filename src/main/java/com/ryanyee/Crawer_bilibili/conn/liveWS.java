package com.ryanyee.Crawer_bilibili.conn;

import com.ryanyee.Crawer_bilibili.model.DanmuMessage;
import com.ryanyee.Crawer_bilibili.protocol.Packet;
import lombok.Data;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.time.Instant;

@Data
public class liveWS {
    public final String host;
    public final int port;
    private final WebSocketClient client;

    public liveWS() {
        this.host = "broadcastlv.chat.bilibili.com";
        this.port = 443;
        this.client = null;
    }

    public liveWS(String host, int port, int userID, int roomID,String key) {
        this.host = host;
        this.port = port;
        URI uri = URI.create("wss://" + host + "/sub");
        this.client = initClient(uri,userID,roomID,key);
    }

    private WebSocketClient initClient(URI uri, int userID, int roomID, String key) {
        return new WebSocketClient(uri) {
            public void onOpen(ServerHandshake handshake) {
                Instant now = Instant.now();
                System.out.println(now);
                byte[] frame = Packet.enterRoomPacket(userID,roomID,key).toFrame();
                send(frame);
            }

            public void onMessage(String message) {
                System.out.println();
                Packet.frameToBuffer(ByteBuffer.wrap(message.getBytes())).thenAccept(packets -> packets.forEach(packet -> {
                    switch (packet.getPacketType()) {
                        case COMMAND:
                            if (packet.getCommand().equals("DANMU_MSG")) {
                                DanmuMessage msg = new DanmuMessage(packet.getCommand());
                                System.out.println(msg.getNickname() + ": " + msg.getMessage());
                            }
                            break;
                    }
                }));
            }

            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Connection closed with reason: " + reason);
                Instant now = Instant.now();
                System.out.println(now);
            }

            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
    }
}
