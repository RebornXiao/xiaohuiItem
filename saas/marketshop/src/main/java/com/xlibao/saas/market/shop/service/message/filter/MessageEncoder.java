package com.xlibao.saas.market.shop.service.message.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author chinahuangxc on 2017/8/8.
 */
public class MessageEncoder extends MessageToMessageEncoder {

    @Override
    protected void encode(ChannelHandlerContext context, Object msg, List out) throws Exception {
        ByteBuf bb = context.alloc().ioBuffer();

        bb.writeBytes(((byte[]) msg));
        // 添加进去
        out.add(bb);
    }

//    @Override
//    protected void encode(ChannelHandlerContext context, Object msg, List<Object> out) throws Exception {
//        ByteBuf bb = context.alloc().ioBuffer();
//        // 填充
////        NettyByteOutputMessage outputMessage = new NettyByteOutputMessage(msg.getId(), bb);
////        MessageCoder coder = coderMgr.getCoderById(msg.getId());
////        if (coder == null) {
////            log.error("\n无法找到 NettyEncoder , NettyDecoder id=" + msg.getId()+"\n");
////            //强制关闭该 session
////            ctx.channel().close();
////            return;
////        }
////        try {
////            coder.output(msg, outputMessage);
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
////        outputMessage.flush();
//        // 添加进去
////        out.add(bb);
//    }
}
