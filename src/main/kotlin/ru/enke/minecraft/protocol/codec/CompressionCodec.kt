package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import io.netty.handler.codec.DecoderException
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt
import java.util.zip.Deflater
import java.util.zip.Inflater

class CompressionCodec @JvmOverloads constructor(var threshold: Int = DEFAULT_COMPRESSION_THRESHOLD) : ByteToMessageCodec<ByteBuf>() {

    companion object {
        const val DEFAULT_COMPRESSION_THRESHOLD = 300
        const val MAXIMUM_COMPRESSION_SIZE = 2_097_152
    }

    private val deflater = Deflater()
    private val inflater = Inflater()
    private val data = ByteArray(8192)

    override fun encode(ctx: ChannelHandlerContext, buf: ByteBuf, out: ByteBuf) {
        val size = buf.readableBytes()

        // Threshold not reached.
        if(threshold > size) {
            out.writeVarInt(0)
            out.writeBytes(buf)
            return
        }

        val bytes = ByteArray(size)
        buf.readBytes(bytes)
        out.writeVarInt(bytes.size)

        deflater.setInput(bytes, 0, size)
        deflater.finish()

        while(!deflater.finished()) {
            deflater.deflate(data)
            out.writeBytes(data)
        }

        deflater.reset()
    }

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if(buf.readableBytes() == 0) {
            return
        }

        val size = buf.readVarInt()

        if(size == 0) {
            out.add(buf.readBytes(buf.readableBytes()))
            return
        }

        if(threshold > size) {
            throw DecoderException("Badly compressed packet: size of $size is below threshold of $threshold")
        }

        if(size > MAXIMUM_COMPRESSION_SIZE) {
            throw DecoderException("Badly compressed packet: size of $size is larger than protocol maximum of $MAXIMUM_COMPRESSION_SIZE")
        }

        val bytes = ByteArray(buf.readableBytes())
        buf.readBytes(bytes)
        inflater.setInput(bytes)
        val inflated = ByteArray(size)
        inflater.inflate(inflated)

        out.add(Unpooled.wrappedBuffer(inflated))
        inflater.reset()
    }

}
