package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import java.util.*

object SpawnObjectPacket : Packet<SpawnObject> {

    override fun write(message: SpawnObject, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeUUID(message.uuid)
        buffer.writeByte(message.type)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeByte((message.yaw * 256 / 360).toInt())
        buffer.writeByte((message.pitch * 256 / 360).toInt())
        buffer.writeInt(message.data)
        buffer.writeShort((message.velocityX * 8000).toInt())
        buffer.writeShort((message.velocityX * 8000).toInt())
        buffer.writeShort((message.velocityX * 8000).toInt())
    }

    override fun read(buffer: ByteBuf): SpawnObject {
        val id = buffer.readVarInt()
        val uuid = buffer.readUUID()
        val type = buffer.readByte().toInt()
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val pitch = buffer.readByte() * 360 / 256F
        val yaw = buffer.readByte() * 360 / 256F
        val data = buffer.readInt()
        val velocityX = (buffer.readShort() / 8000).toDouble()
        val velocityY = (buffer.readShort() / 8000).toDouble()
        val velocityZ = (buffer.readShort() / 8000).toDouble()

        return SpawnObject(id, uuid, type, x, y, z, pitch, yaw, data, velocityX, velocityY, velocityZ)
    }

}

data class SpawnObject(val id: Int,
                       val uuid: UUID,
                       val type: Int,
                       val x: Double,
                       val y: Double,
                       val z: Double,
                       val pitch: Float,
                       val yaw: Float,
                       val data: Int,
                       val velocityX: Double,
                       val velocityY: Double,
                       val velocityZ: Double) : PacketMessage
