package ru.enke.minecraft.protocol.packet.server.status

import io.netty.buffer.Unpooled
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.Protocol
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.data.status.Player
import ru.enke.minecraft.protocol.packet.data.status.Players
import ru.enke.minecraft.protocol.packet.data.status.ServerStatusInfo
import ru.enke.minecraft.protocol.packet.data.status.Version
import java.util.*

class StatusResponsePacketTest {

    private val packet = StatusResponsePacket
    private val buffer = Unpooled.buffer()

    @Test
    fun testReadAndWrite() {
        val version = Version("Test", Protocol.VERSION)
        val description = Message("text").append("other text")
        val players = Players(50, 100, arrayListOf(Player(UUID.randomUUID(), "user")))
        val status = ServerStatusInfo(version, description, players)

        packet.write(StatusResponse(status), buffer)
        assertEquals(status, packet.read(buffer).statusInfo)
    }

}
