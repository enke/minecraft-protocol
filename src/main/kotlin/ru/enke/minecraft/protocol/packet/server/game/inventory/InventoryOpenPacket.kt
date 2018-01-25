package ru.enke.minecraft.protocol.packet.server.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object InventoryOpenPacket : Packet<InventoryOpen> {

    override fun write(message: InventoryOpen, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeString(message.type)
        buffer.writeString(message.title.toJson())
        buffer.writeByte(message.size)
    }

    override fun read(buffer: ByteBuf): InventoryOpen {
        val windowId = buffer.readByte().toInt()
        val type = buffer.readString()
        val title = Message.fromJson(buffer.readString())
        val size = buffer.readByte().toInt()

        return InventoryOpen(windowId, type, title, size)
    }

}

data class InventoryOpen(val windowId: Int, val type: String, val title: Message, val size: Int) : PacketMessage
