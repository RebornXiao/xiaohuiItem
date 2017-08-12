package com.xlibao.saas.market.core.message.hardware;

import com.xlibao.io.entry.HeartbeatExtension;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class HeartbeatExtensionImpl implements HeartbeatExtension {

    @Override
    public String heartbeatParameters() {
        return "";
    }

    @Override
    public void notifyHeartbeatResult(long userKey, String callbackValue) {
    }
}