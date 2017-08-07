/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlibao.io.service.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 输出数据时，编码 ，即 output message 消息长度，消息体
 *
 * @author zhumg
 * @datetime 2016-7-31 17:46:44
 */
public class NettyEncoder extends MessageToMessageEncoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(NettyEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        ByteBuf bb = ctx.alloc().ioBuffer();
        // 填充
//        NettyByteOutputMessage outputMessage = new NettyByteOutputMessage(msg.getId(), bb);
//        MessageCoder coder = coderMgr.getCoderById(msg.getId());
//        if (coder == null) {
//            log.error("\n无法找到 NettyEncoder , NettyDecoder id=" + msg.getId()+"\n");
//            //强制关闭该 session
//            ctx.channel().close();
//            return;
//        }
//        try {
//            coder.output(msg, outputMessage);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        outputMessage.flush();
         // 添加进去
//        out.add(bb);
    }
}
