package com.xlibao.saas.market.core.service.application;

import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.NettySession;

/**
 * @author chinahuangxc on 2017/8/13.
 */
public interface ApplicationService {

    void logicMessageExecute(NettySession session, MessageInputStream message);

    void platformMessageExecute(NettySession session, MessageInputStream message);

    void hardwareMessageExecute(NettySession session, MessageInputStream message);
}