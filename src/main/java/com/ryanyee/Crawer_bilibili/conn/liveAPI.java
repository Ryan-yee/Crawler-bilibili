package com.ryanyee.Crawer_bilibili.conn;

import com.ryanyee.Crawer_bilibili.model.DanmuConf;
import com.ryanyee.Crawer_bilibili.model.RoomData;
import com.ryanyee.Crawer_bilibili.utils.httpUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class liveAPI {
    public final String baseURL;
    private final HttpClient client;

    public liveAPI() {
        this.baseURL = "https://api.live.bilibili.com";
        this.client = HttpClient.newHttpClient();
    }

    public liveAPI(String baseURL) {
        this.baseURL = baseURL;
        this.client = HttpClient.newHttpClient();
    }

    public RoomData fetchRoomData(int roomID) throws IOException, InterruptedException, URISyntaxException {
        String url = baseURL + "/room/v1/Room/mobileRoomInit?id=" + roomID;
        HttpResponse<String> response = httpUtils.get(client,url);
        return httpUtils.parseResponse(response.body(), RoomData.class);
    }

    public DanmuConf fetchDanmuConf(int roomID) throws IOException, InterruptedException, URISyntaxException {
        String url = baseURL + "/room/v1/Danmu/getConf?id=" + roomID;
        HttpResponse<String> response = httpUtils.get(client,url);
        return httpUtils.parseResponse(response.body(), DanmuConf.class);
    }
}
