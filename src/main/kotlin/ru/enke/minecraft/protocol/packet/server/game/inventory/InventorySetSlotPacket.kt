package ru.enke.minecraft.protocol.packet.server.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Slot
import ru.enke.minecraft.protocol.packet.readSlot
import ru.enke.minecraft.protocol.packet.writeSlot

object InventorySetSlotPacket : Packet<InventorySetSlot> {

    override fun write(message: InventorySetSlot, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeShort(message.slotIndex)
        buffer.writeSlot(message.slot)
    }

    override fun read(buffer: ByteBuf): InventorySetSlot {
        val windowId = buffer.readByte().toInt()
        val slotIndex = buffer.readShort().toInt()
        val slot = buffer.readSlot()

        return InventorySetSlot(windowId, slotIndex, slot)
    }

}

data class InventorySetSlot(val windowId: Int, val slotIndex: Int, val slot: Slot?) : PacketMessage
