package ru.enke.minecraft.protocol.packet

import io.netty.buffer.Unpooled
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.enke.minecraft.protocol.packet.data.game.GameMode
import ru.enke.minecraft.protocol.packet.data.game.GameMode.CREATIVE
import ru.enke.minecraft.protocol.packet.data.game.Position
import ru.enke.minecraft.protocol.packet.data.game.Slot

class PacketTest {

    private val buffer = Unpooled.buffer()

    @Test
    fun testVarInt() {
        buffer.writeVarInt(Int.MAX_VALUE)
        assertEquals(Int.MAX_VALUE, buffer.readVarInt())
    }

    @Test
    fun testVarLong() {
        buffer.writeVarLong(150000)
        assertEquals(150000, buffer.readVarLong())
    }

    @Test
    fun testString() {
        buffer.writeString("Hello world")
        assertEquals("Hello world", buffer.readString())
    }

    @Test
    fun testEnum() {
        buffer.writeEnum(CREATIVE)
        assertEquals(CREATIVE, buffer.readEnum<GameMode>())
    }

    @Test
    fun testPosition() {
        val position = Position(1, 2, 3)

        buffer.writePosition(position)
        assertEquals(position, buffer.readPosition())
    }

    @Test
    fun testSlot() {
        val slot = Slot(1, 64, 0)

        buffer.writeSlot(slot)
        assertEquals(slot, buffer.readSlot())
    }

}
