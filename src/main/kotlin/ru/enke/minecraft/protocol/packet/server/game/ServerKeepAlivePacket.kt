package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object ServerKeepAlivePacket : Packet<ServerKeepAlive> {

    override fun write(message: ServerKeepAlive, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
    }

    override fun read(buffer: ByteBuf): ServerKeepAlive {
        return ServerKeepAlive(buffer.readVarInt())
    }

}

data class ServerKeepAlive(val id: Int) : PacketMessage