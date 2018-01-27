package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.codec.DecoderException
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeString
import ru.enke.minecraft.protocol.packet.writeVarInt

class CompressionCodecTest {

    companion object {
        private const val HELLO_STRING = "Hello compression codec"
    }

    private val compressionCodec = CompressionCodec(50)
    private val channel = EmbeddedChannel(compressionCodec)
    private val buffer = Unpooled.buffer()

    @Test
    fun testEncodeNotReachedThreshold() {
        buffer.writeString(HELLO_STRING)
        channel.writeOutbound(buffer)

        val buffer2 = channel.readOutbound<ByteBuf>()
        assertEquals(0, buffer2.readVarInt())
        assertEquals(HELLO_STRING, buffer2.readString())
    }

    @Test
    fun testDecodeNotCompressed() {
        buffer.writeVarInt(0)
        buffer.writeString(HELLO_STRING)
        channel.writeInbound(buffer)

        val buffer2 = channel.readInbound<ByteBuf>()
        assertEquals(HELLO_STRING, buffer2.readString())
    }

    @Test(expected = DecoderException::class)
    fun testDecodeBelowThreshold() {
        buffer.writeVarInt(25)
        channel.writeInbound(buffer)
    }

    @Test(expected = DecoderException::class)
    fun testDecodeLargerMaximum() {
        buffer.writeVarInt(Integer.MAX_VALUE)
        channel.writeInbound(buffer)
    }

    @Test
    fun testCompression() {
        for(i in 0..5) {
            buffer.writeString(HELLO_STRING)
            buffer.writeInt(i)
        }

        channel.writeOutbound(buffer)
        channel.writeInbound(channel.readOutbound())

        val buffer2 = channel.readInbound<ByteBuf>()

        for(i in 0..5) {
            assertEquals(HELLO_STRING, buffer2.readString())
            assertEquals(i, buffer2.readInt())
        }
    }

}
