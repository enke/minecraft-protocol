package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object ClientKeepAlivePacket : Packet<ClientKeepAlive> {

    override fun write(message: ClientKeepAlive, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
    }

    override fun read(buffer: ByteBuf): ClientKeepAlive {
        return ClientKeepAlive(buffer.readVarInt())
    }

}

data class ClientKeepAlive(val id: Int) : PacketMessage
