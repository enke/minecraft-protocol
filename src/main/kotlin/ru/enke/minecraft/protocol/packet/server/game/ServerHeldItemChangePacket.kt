package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ServerItemHeldChangePacket : Packet<ServerItemHeldChange> {

    override fun write(message: ServerItemHeldChange, buffer: ByteBuf) {
        buffer.writeByte(message.slot)
    }

    override fun read(buffer: ByteBuf): ServerItemHeldChange {
        return ServerItemHeldChange(buffer.readByte().toInt())
    }

}

data class ServerItemHeldChange(val slot: Int) : PacketMessage