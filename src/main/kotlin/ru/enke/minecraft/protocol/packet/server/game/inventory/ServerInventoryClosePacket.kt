package ru.enke.minecraft.protocol.packet.server.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ServerInventoryClosePacket : Packet<ServerInventoryClose> {

    override fun write(message: ServerInventoryClose, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
    }

    override fun read(buffer: ByteBuf): ServerInventoryClose {
        return ServerInventoryClose(buffer.readByte().toInt())
    }

}

data class ServerInventoryClose(val windowId: Int) : PacketMessage
