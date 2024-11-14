package com.ryanyee.Crawer_bilibili.model;

import lombok.Data;

@Data
public class CommonResp<T> {
    private int code;
    private String msg;
    private String message;
    private T data;
}
