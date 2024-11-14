package com.ryanyee.Crawer_bilibili.model;

import lombok.Data;

@Data
public class DanmuConf {
    private int refresh_row_factor;
    private int refresh_rate;
    private int max_delay;
    private int port;
    private String host;
    private HostServer[] host_server_list;
    private Server[] server_list;
    private String token;
}

@Data
class HostServer {
    private String host;
    private int port;
    private int wss_port;
    private int ws_port;
}

@Data
class Server {
    private String host;
    private int port;
}

