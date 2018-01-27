package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.codec.CorruptedFrameException
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeString
import ru.enke.minecraft.protocol.packet.writeVarInt

class LengthCodecTest {

    companion object {
        private const val HELLO_STRING = "Hello length codec"
        private const val HELLO_STRING_LENGTH = 1 + HELLO_STRING.length // 1 var int + 18 string
    }

    private val lengthCodec = LengthCodec()
    private val channel = EmbeddedChannel(lengthCodec)
    private val buffer = Unpooled.buffer()

    @Test
    fun testEncode() {
        buffer.writeString(HELLO_STRING)
        channel.writeOutbound(buffer)

        val buffer2 = channel.readOutbound<ByteBuf>()
        assertEquals(HELLO_STRING_LENGTH, buffer2.readVarInt())
        assertEquals(HELLO_STRING, buffer2.readString())
    }

    @Test
    fun testDecode() {
        buffer.writeVarInt(HELLO_STRING_LENGTH)
        buffer.writeString(HELLO_STRING)
        channel.writeInbound(buffer)

        val buffer2 = channel.readInbound<ByteBuf>()
        assertEquals(HELLO_STRING, buffer2.readString())
    }

    @Test(expected = CorruptedFrameException::class)
    fun testDecodeCorrupted() {
        buffer.writeByte(-1)
        buffer.writeByte(-2)
        buffer.writeByte(-3)
        buffer.writeString(HELLO_STRING)
        channel.writeInbound(buffer)
    }

}
