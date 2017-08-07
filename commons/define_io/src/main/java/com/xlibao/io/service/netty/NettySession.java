package com.xlibao.io.service.netty;

import io.netty.channel.Channel;

public class NettySession {

    private String ip;
    private int port;
    private Channel channel;
    private String id;

    public NettySession() {
    }

    protected final void init(Channel channel) {
        this.channel = channel;
        this.id = channel.id().asShortText();
        this.ip = "192.168.1.1";
        this.port = 1212;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Channel getChannel() {
        return channel;
    }
}