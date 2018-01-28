package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object SpawnGlobalEntityPacket : Packet<SpawnGlobalEntity> {

    override fun write(message: SpawnGlobalEntity, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeByte(message.type)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
    }

    override fun read(buffer: ByteBuf): SpawnGlobalEntity {
        val id = buffer.readVarInt()
        val type = buffer.readByte().toInt()
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()

        return SpawnGlobalEntity(id, type, x, y, z)
    }

}

data class SpawnGlobalEntity(val id: Int,
                             val type: Int,
                             val x: Double,
                             val y: Double,
                             val z: Double) : PacketMessage
