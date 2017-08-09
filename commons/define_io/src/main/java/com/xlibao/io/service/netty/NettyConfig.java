package com.xlibao.io.service.netty;

public class NettyConfig {

    /** 空闲类型 -- 读 */
    public static final int TIME_OUT_READER = 0;
    /** 空闲类型 -- 写 */
    public static final int TIME_OUT_WRITER = 1;
    /** 空闲类型 -- 读、写 */
    public static final int TIME_OUT_BOTH = 2;

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