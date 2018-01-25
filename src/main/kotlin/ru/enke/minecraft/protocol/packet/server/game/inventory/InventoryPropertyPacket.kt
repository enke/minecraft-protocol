package ru.enke.minecraft.protocol.packet.server.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object InventoryPropertyPacket : Packet<InventoryProperty> {

    override fun write(message: InventoryProperty, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeShort(message.property)
        buffer.writeShort(message.value)
    }

    override fun read(buffer: ByteBuf): InventoryProperty {
        val windowId = buffer.readByte().toInt()
        val property = buffer.readShort().toInt()
        val value = buffer.readShort().toInt()

        return InventoryProperty(windowId, property, value)
    }

}

data class InventoryProperty(val windowId: Int, val property: Int, val value: Int) : PacketMessage
