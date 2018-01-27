package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class EncryptionCodecTest {

    private val sharedSecret = createSharedSecret()
    private val encryptionCodec = EncryptionCodec(sharedSecret)
    private val channel = EmbeddedChannel(encryptionCodec)
    private val buffer = Unpooled.buffer()

    private fun createSharedSecret(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
    }

    @Test
    fun testIsEncrypted() {
        for(i in 0..5) {
            buffer.writeLong(35)
            buffer.writeInt(i)
        }

        channel.writeOutbound(buffer)
        val buffer2 = channel.readOutbound<ByteBuf>()

        for(i in 0..5) {
            assertNotEquals(35, buffer2.readLong())
            assertNotEquals(i, buffer2.readInt())
        }
    }

    @Test
    fun testEncryption() {
        for(i in 0..5) {
            buffer.writeLong(35)
            buffer.writeInt(i)
        }

        channel.writeOutbound(buffer)
        channel.writeInbound(channel.readOutbound())

        val buffer2 = channel.readInbound<ByteBuf>()

        for(i in 0..5) {
            assertEquals(35, buffer2.readLong())
            assertEquals(i, buffer2.readInt())
        }
    }

}
