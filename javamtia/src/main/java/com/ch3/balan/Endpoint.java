package com.ch3.balan;

/**
 * Created by OKali on 2019/1/13.
 * 标识下游部件的节点
 */
public class Endpoint {

    public final String host;
    public final int port;
    public final int weight;

    private volatile boolean online = true;

    public Endpoint(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
