package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.Unpooled
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.packet.data.game.Statistic

class StatisticsPacketTest {

    private val packet = StatisticsPacket
    private val buffer = Unpooled.buffer()

    @Test
    fun testReadAndWrite() {
        val list = mutableListOf<Statistic>()
        list.add(Statistic("key", 1))
        list.add(Statistic("key2", 2))
        list.add(Statistic("key3", 3))
        val statistics = Statistics(list)

        packet.write(statistics, buffer)
        assertEquals(statistics, packet.read(buffer))
    }

}
