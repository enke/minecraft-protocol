package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import io.netty.handler.codec.CorruptedFrameException
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

class LengthCodec : ByteToMessageCodec<ByteBuf>() {

    override fun encode(ctx: ChannelHandlerContext, buf: ByteBuf, out: ByteBuf) {
        val bodyLength = buf.readableBytes()
        out.ensureWritable(bodyLength + 3)
        out.writeVarInt(bodyLength)
        out.writeBytes(buf)
    }

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val lengthBytes = ByteArray(3)
        buf.markReaderIndex()

        for(i in lengthBytes.indices) {
            if(!buf.isReadable) {
                buf.resetReaderIndex()
                return
            }

            lengthBytes[i] = buf.readByte()

            if(lengthBytes[i] >= 0) {
                val length = Unpooled.wrappedBuffer(lengthBytes).readVarInt()

                if(length > buf.readableBytes()) {
                    buf.resetReaderIndex()
                } else {
                    out.add(buf.readBytes(length))
                }

                return
            }
        }

        throw CorruptedFrameException("Length is more than 21 bits")
    }

}
