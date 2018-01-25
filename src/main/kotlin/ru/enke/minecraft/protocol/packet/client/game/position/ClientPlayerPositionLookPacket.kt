package ru.enke.minecraft.protocol.packet.client.game.position

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ClientPlayerPositionLookPacket : Packet<ClientPlayerPositionLook> {

    override fun write(message: ClientPlayerPositionLook, buffer: ByteBuf) {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
        buffer.writeBoolean(message.ground)
    }

    override fun read(buffer: ByteBuf): ClientPlayerPositionLook {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()
        val ground = buffer.readBoolean()

        return ClientPlayerPositionLook(x, y, z, yaw, pitch, ground)
    }

}

data class ClientPlayerPositionLook(val x: Double, val y: Double, val z: Double, val yaw: Float,
                                    val pitch: Float, val ground: Boolean) : PacketMessage