package com.ryanyee.Crawer_bilibili.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ryanyee.Crawer_bilibili.model.CommonResp;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class httpUtils {
    public static HttpResponse<String> get(@NotNull HttpClient client, String url) throws IOException,InterruptedException {
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> T parseResponse(String response, Class<T> clazz) {
        CommonResp<T> resp = JSON.parseObject(response, new TypeReference<>() {});
        JSONObject dataJsonObject = (JSONObject) resp.getData();
        return dataJsonObject.toJavaObject(clazz);
    }
}