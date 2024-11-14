package com.ryanyee.Crawer_bilibili.protocol;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.Inflater;

import static com.ryanyee.Crawer_bilibili.utils.buvidUtils.getBuvidByWifiMac;

@Setter
@Getter
public class Packet {

    private static final int HEADER_LENGTH = 0x10;

    private int protocol;
    private PacketType packetType;
    private int tag;
    private byte[] content;

    public Packet(int protocol, PacketType packetType, int tag, byte[] content) {
        this.protocol = protocol;
        this.packetType = packetType;
        this.tag = tag;
        this.content = content;
    }

    public int getTotalLength() {
        return HEADER_LENGTH + content.length;
    }

    public int getPopularity() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(content);
        return byteBuffer.getInt(0);
    }

    public Object getCommand() {
        String json = new String(content);
        return JSON.parse(json);
    }

    public byte[] toFrame() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getTotalLength());
        byteBuffer.putInt(getTotalLength());
        byteBuffer.putShort((short) HEADER_LENGTH);
        byteBuffer.putShort((short) protocol);
        byteBuffer.putInt(packetType.getValue());
        byteBuffer.putInt(tag);
        byteBuffer.put(content);
        return byteBuffer.array();
    }

    public static Packet enterRoomPacket(int anchorUserId, int roomId, String key) {
        String jsonString = JSON.toJSONString(new EnterRoomRequest(anchorUserId, roomId, key));
        byte[] content = jsonString.getBytes(StandardCharsets.UTF_8);
        return new Packet(0, PacketType.ENTER_ROOM, 1, content);
    }

    @Setter
    @Getter
    public static class EnterRoomRequest {
        private int uid;
        private int roomid;
        private int protover = 3;
//        private String buvid;
        private String platform = "web";
//        private String clientver = "2.0.11";
        private int type = 2;
        private String key;

        public EnterRoomRequest(int uid, int roomid, String key) {
            this.uid = uid;
            this.roomid = roomid;
            this.key = key;
//            this.buvid = getBuvidByWifiMac();
        }

    }

    public static CompletableFuture<List<Packet>> frameToBuffer(ByteBuffer frame) {
        return CompletableFuture.supplyAsync(() -> {
            List<Packet> packets = new ArrayList<>();
            int bufferLength = frame.limit();
            while (frame.position() < bufferLength) {
                int totalLength = frame.getInt(); // Read total length
                int headerLength = frame.getShort(); // Fixed 16
                int protocol = frame.getShort(); // Current v2
                PacketType packetType = PacketType.getPacketType(frame.getInt());
                int tag = frame.getInt();
                byte[] content = new byte[totalLength - headerLength];
                frame.get(content);

                if (protocol == 2) { // If the protocol is 2, we need to decompress
                    byte[] decompressedContent = decompress(content);
                    // Recursively handle decompressed content
                    packets.addAll(frameToBuffer(ByteBuffer.wrap(decompressedContent)).join());
                } else {
                    packets.add(new Packet(protocol, packetType, tag, content));
                    if (protocol == 1) { // Heartbeat response handling
                        break; // Stop parsing if protocol is 1
                    }
                }
            }
            return packets;
        });
    }

    private static byte[] decompress(byte[] content) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(content);
            byte[] output = new byte[content.length * 2]; // Initial size guess
            int decompressedDataLength = inflater.inflate(output);
            byte[] decompressedData = new byte[decompressedDataLength];
            System.arraycopy(output, 0, decompressedData, 0, decompressedDataLength);
            inflater.end();
            return decompressedData;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
