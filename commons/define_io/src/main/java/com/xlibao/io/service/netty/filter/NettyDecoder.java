package com.xlibao.io.service.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class NettyDecoder extends ByteToMessageDecoder {

    public NettyDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        // 如果大于4位
        if (length < 4) {
            return;
        }
        int index = in.readerIndex();
        int size = in.readInt();
        if (length < size) {
            in.readerIndex(index);
            return;
        }
        // 读取ID
        int id = in.readInt();
        //System.err.println("decode消息，id=" + id + ", length：" + length + ", size：" + size);
        // 直接生成内容
        MessageCoder<Message> coder = coderMgr.getCoderById(id);
        if (coder == null) {
            throw new RuntimeException("无法找到 MessageCoder , NettyDecoder id=" + id);
        }
        NettyByteInputMessage inputMessage = new NettyByteInputMessage(id, in);
        Message message = coder.input(inputMessage);
        //如果读取的数据还有大把，则代表读数据出错，提示
        if (inputMessage.getIndex() < size) {
            System.err.println("未完整读完消息，可能出错：id=" + id + ", 当前读到：" + inputMessage.getIndex() + ", 消息长度：" + size);
        }
        out.add(message);
    }
}
