package com.xlibao.saas.market.shop.service.message.filter;

import com.xlibao.common.CommonUtils;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.impl.MessageInputStreamImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author chinahuangxc on 2017/8/8.
 */
public class MessageDecoder extends ByteToMessageDecoder {

    // 16进制数字字符集
    private static String hexString = "0123456789ABCDEF";
    // 结束符
    private static String END_CHARACTER = "FFFF";

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> messages) throws Exception {
        int length = byteBuf.readableBytes();
        // 如果大于4位
        int position = byteBuf.readerIndex();
        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        String parameter = decode(bytes);
        if (!parameter.contains(END_CHARACTER)) { // 不包含结束符
            byteBuf.readerIndex(position);
            return;
        }
        int index = parameter.lastIndexOf(END_CHARACTER);

        parameter = parameter.substring(0, index + 4);
        int currentIndex = hex2byte(parameter).length;
        if (currentIndex < bytes.length) {
            // 设置位置
            byteBuf.readerIndex(currentIndex);
        }
        String[] values = parameter.split(END_CHARACTER);
        for (String value : values) {
            MessageInputStream messageInputStream = new MessageInputStreamImpl(value.getBytes());
            messages.add(messageInputStream);
        }
    }

    private String decode(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(hexString.charAt((b & 0xf0) >> 4));
            builder.append(hexString.charAt((b & 0x0f)));
        }
        return builder.toString();
    }

    private static byte[] hex2byte(String hex) {
        hex = hex.replaceAll(CommonUtils.SPACE, "");

        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];

        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (hexString.indexOf(hex2char[2 * p]) * 16);
            temp += hexString.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }
}