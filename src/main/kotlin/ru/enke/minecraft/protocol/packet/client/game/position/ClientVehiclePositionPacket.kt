package ru.enke.minecraft.protocol.packet.client.game.position

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ClientVehiclePositionPacket : Packet<ClientVehiclePosition> {

    override fun write(message: ClientVehiclePosition, buffer: ByteBuf) {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
    }

    override fun read(buffer: ByteBuf): ClientVehiclePosition {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()

        return ClientVehiclePosition(x, y, z, yaw, pitch)
    }

}

data class ClientVehiclePosition(val x: Double, val y: Double, val z: Double, val yaw: Float,
                                 val pitch: Float) : PacketMessage
