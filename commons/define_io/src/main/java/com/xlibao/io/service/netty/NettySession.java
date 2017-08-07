package com.xlibao.io.service.netty;

import com.xlibao.io.entry.MessageOutputStream;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettySession {

    private String ip;
    private int port;
    private Channel channel;
    private String id;

    private Map<String, Object> attributes = new HashMap<>();

    public NettySession() {
    }

    public void init(Channel channel) {
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

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void send(MessageOutputStream message) {
        this.channel.writeAndFlush(message);
    }
}