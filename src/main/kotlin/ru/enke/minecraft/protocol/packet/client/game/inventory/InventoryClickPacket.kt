package ru.enke.minecraft.protocol.packet.client.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Slot
import ru.enke.minecraft.protocol.packet.readSlot
import ru.enke.minecraft.protocol.packet.writeSlot

object InventoryClickPacket : Packet<InventoryClick> {

    override fun write(message: InventoryClick, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeShort(message.slotIndex)
        buffer.writeByte(message.button)
        buffer.writeShort(message.transaction)
        buffer.writeByte(message.mode)
        buffer.writeSlot(message.slot)
    }

    override fun read(buffer: ByteBuf): InventoryClick {
        val windowId = buffer.readByte().toInt()
        val slotIndex = buffer.readShort().toInt()
        val button = buffer.readByte().toInt()
        val transaction = buffer.readShort().toInt()
        val mode = buffer.readByte().toInt()
        val slot = buffer.readSlot()

        return InventoryClick(windowId, slotIndex, button, transaction, mode, slot)
    }

}

data class InventoryClick(val windowId: Int, val slotIndex: Int, val button: Int, val transaction: Int,
                          val mode: Int, val slot: Slot?) : PacketMessage
