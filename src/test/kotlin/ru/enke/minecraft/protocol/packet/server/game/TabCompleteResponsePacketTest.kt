package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.Unpooled
import org.junit.Assert
import org.junit.Test

class TabCompleteResponsePacketTest {

    private val packet = TabCompleteResponsePacket
    private val buffer = Unpooled.buffer()

    @Test
    fun testReadAndWrite() {
        val tabCompleteResponse = TabCompleteResponse(mutableListOf("hello", "world", "protocol"))

        packet.write(tabCompleteResponse, buffer)
        Assert.assertEquals(tabCompleteResponse, packet.read(buffer))
    }

}
