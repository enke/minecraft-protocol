package ru.enke.minecraft.protocol.packet.server.game.player

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.writeVarInt

object ServerPlayerPositionAndLookPacket : Packet<ServerPlayerPositionLook> {

    override fun write(message: ServerPlayerPositionLook, buffer: ByteBuf) {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
        buffer.writeByte(message.flags)
        buffer.writeVarInt(message.teleportId)
    }

    override fun read(buffer: ByteBuf): ServerPlayerPositionLook {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()
        val flags = buffer.readByte().toInt()
        val teleportId = buffer.readInt()

        return ServerPlayerPositionLook(x, y, z, yaw, pitch, flags, teleportId)
    }

}

data class ServerPlayerPositionLook(val x: Double, val y: Double, val z: Double, val yaw: Float,
                                    val pitch: Float, val flags: Int, val teleportId: Int) : PacketMessage