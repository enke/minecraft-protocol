package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object EntityStatusPacket : Packet<EntityStatus> {

    override fun write(message: EntityStatus, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeByte(message.status)
    }

    override fun read(buffer: ByteBuf): EntityStatus {
        val id = buffer.readVarInt()
        val status = buffer.readByte().toInt()

        return EntityStatus(id, status)
    }

}

data class EntityStatus(val id: Int, val status: Int) : PacketMessage
