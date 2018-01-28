package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.EntityMetadata
import java.util.*

object SpawnMobPacket : Packet<SpawnMob> {

    override fun write(message: SpawnMob, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeUUID(message.uuid)
        buffer.writeByte(message.type)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeByte((message.yaw * 256 / 360).toInt())
        buffer.writeByte((message.pitch * 256 / 360).toInt())
        buffer.writeByte((message.headYaw * 256 / 360).toInt())
        buffer.writeShort((message.velocityX * 8000).toInt())
        buffer.writeShort((message.velocityX * 8000).toInt())
        buffer.writeShort((message.velocityX * 8000).toInt())
        buffer.writeEntityMetadata(message.metadata)
    }

    override fun read(buffer: ByteBuf): SpawnMob {
        val id = buffer.readVarInt()
        val uuid = buffer.readUUID()
        val type = buffer.readByte().toInt()
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val pitch = buffer.readByte() * 360 / 256F
        val yaw = buffer.readByte() * 360 / 256F
        val headYaw = buffer.readByte() * 360 / 256F
        val velocityX = (buffer.readShort() / 8000).toDouble()
        val velocityY = (buffer.readShort() / 8000).toDouble()
        val velocityZ = (buffer.readShort() / 8000).toDouble()
        val metadata = buffer.readEntityMetadata()

        return SpawnMob(id, uuid, type, x, y, z, pitch, yaw, headYaw, velocityX, velocityY, velocityZ, metadata)
    }

}

data class SpawnMob(val id: Int,
                    val uuid: UUID,
                    val type: Int,
                    val x: Double,
                    val y: Double,
                    val z: Double,
                    val pitch: Float,
                    val yaw: Float,
                    val headYaw: Float,
                    val velocityX: Double,
                    val velocityY: Double,
                    val velocityZ: Double,
                    val metadata: List<EntityMetadata>) : PacketMessage
