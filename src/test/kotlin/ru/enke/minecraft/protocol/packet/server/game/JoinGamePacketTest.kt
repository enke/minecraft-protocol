package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.Unpooled
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.packet.data.game.Difficulty.EASY
import ru.enke.minecraft.protocol.packet.data.game.GameMode.SURVIVAL
import ru.enke.minecraft.protocol.packet.data.game.WorldType.DEBUG

class JoinGamePacketTest {

    private val packet = JoinGamePacket
    private val buffer = Unpooled.buffer()

    @Test
    fun testReadAndWrite() {
        val joinGame = JoinGame(153, SURVIVAL, 15, EASY, 100, DEBUG)

        packet.write(joinGame, buffer)
        assertEquals(joinGame, packet.read(buffer))
    }

}
