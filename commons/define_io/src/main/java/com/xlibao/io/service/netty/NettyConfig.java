package com.xlibao.io.service.netty;

public class NettyConfig {

    // 写超时
    private int writeOutTime = 10;
    // 读超时
    private int readOutTime = 10;
    // 读写同时超时
    private int bothOutTime = 15;

    public void setReadOutTime(int readOutTime) {
        this.readOutTime = readOutTime;
    }

    public void setWriteOutTime(int writeOutTime) {
        this.writeOutTime = writeOutTime;
    }

    public void setBothOutTime(int bothOutTime) {
        this.bothOutTime = bothOutTime;
    }

    int getReadOutTime() {
        return readOutTime;
    }

    int getWriteOutTime() {
        return writeOutTime;
    }

    int getBothOutTime() {
        return bothOutTime;
    }
}