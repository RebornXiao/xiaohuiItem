package com.xlibao.io.service.netty.filter;

import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.entry.impl.MessageOutputStreamImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyDecoder extends ByteToMessageDecoder {

    public NettyDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> out) throws Exception {
        int length = byteBuf.readableBytes();
        // 如果大于4位
        if (length < 4) {
            return;
        }
        int position = byteBuf.readerIndex();
        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        String parameter = decode(bytes);
        if (!parameter.contains("FFFF")) { // 不包含结束符
            byteBuf.readerIndex(position);
        }
        int index = parameter.lastIndexOf("FFFF");

        parameter = parameter.substring(0, index + 4);
        int currentIndex = parameter.getBytes().length;
        if (currentIndex < bytes.length) {
            // 设置位置
            byteBuf.readerIndex(currentIndex);
        }

        String[] message = parameter.split("FFFF");
        for (String m : message) {
            m += "FFFF";
            byte[] s = m.getBytes();
            MessageOutputStream messageOutputStream = new MessageOutputStreamImpl(s.length);
            out.add(messageOutputStream);
        }

    }

    // 16进制数字字符集
    private static String hexString = "0123456789ABCDEF";

    private String decode(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(hexString.charAt((b & 0xf0) >> 4));
            builder.append(hexString.charAt((b & 0x0f)));
        }
        return builder.toString();
    }
}
