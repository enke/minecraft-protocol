package ru.enke.minecraft.protocol.packet.server.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Slot
import ru.enke.minecraft.protocol.packet.readSlot
import ru.enke.minecraft.protocol.packet.writeSlot

object InventoryItemsPacket : Packet<InventoryItems> {

    override fun write(message: InventoryItems, buffer: ByteBuf) {
        val slots = message.slots

        buffer.writeByte(message.windowId)
        buffer.writeShort(slots.size)

        for(slot in slots) {
            buffer.writeSlot(slot)
        }
    }

    override fun read(buffer: ByteBuf): InventoryItems {
        val windowId = buffer.readByte().toInt()
        val slots = mutableListOf<Slot?>()

        for(i in 0..buffer.readShort() - 1) {
            slots.add(buffer.readSlot())
        }

        return InventoryItems(windowId, slots)
    }

}

data class InventoryItems(val windowId: Int, val slots: List<Slot?>) : PacketMessage
