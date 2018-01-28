package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.EntityMetadata
import java.util.*

object SpawnPlayerPacket : Packet<SpawnPlayer> {

    override fun write(message: SpawnPlayer, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeUUID(message.uuid)
        buffer.writeByte(message.type)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeByte((message.yaw * 256 / 360).toInt())
        buffer.writeByte((message.pitch * 256 / 360).toInt())
        buffer.writeEntityMetadata(message.metadata)
    }

    override fun read(buffer: ByteBuf): SpawnPlayer {
        val id = buffer.readVarInt()
        val uuid = buffer.readUUID()
        val type = buffer.readByte().toInt()
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val pitch = buffer.readByte() * 360 / 256F
        val yaw = buffer.readByte() * 360 / 256F
        val metadata = buffer.readEntityMetadata()

        return SpawnPlayer(id, uuid, type, x, y, z, pitch, yaw, metadata)
    }

}

data class SpawnPlayer(val id: Int,
                       val uuid: UUID,
                       val type: Int,
                       val x: Double,
                       val y: Double,
                       val z: Double,
                       val pitch: Float,
                       val yaw: Float,
                       val metadata: List<EntityMetadata>) : PacketMessage
