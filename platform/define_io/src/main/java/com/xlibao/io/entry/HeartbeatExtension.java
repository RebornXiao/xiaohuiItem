package com.xlibao.io.entry;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface HeartbeatExtension {

    String hearbeatParameters();

    void notifyHearbeatResult(long userKey, String callbackValue);
}