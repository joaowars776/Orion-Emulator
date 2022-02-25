package com.orionemu.server.network.messages.protocol.codec;

import com.orionemu.server.network.messages.protocol.security.RC4;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class EncryptionDecoder extends ByteToMessageDecoder {

    private final RC4 rc4;

    public EncryptionDecoder(byte[] key) {
        this.rc4 = new RC4(key);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf result = Unpooled.buffer();

        while (in.readableBytes() > 0) {
            result.writeByte((byte) (in.readByte() ^ this.rc4.next()));
        }
        System.out.println(new String(result.array()));
        out.add(result);
    }
}
