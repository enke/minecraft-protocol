package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import kotlin.experimental.and
import kotlin.experimental.or

object SteerVehiclePacket : Packet<SteerVehicle> {

    override fun write(message: SteerVehicle, buffer: ByteBuf) {
        buffer.writeFloat(message.sideways)
        buffer.writeFloat(message.forward)

        var flags: Byte = 0

        if(message.jump) {
            flags = flags or 1
        }

        if(message.dismount) {
            flags = flags or 2
        }

        buffer.writeByte(flags.toInt())
    }

    override fun read(buffer: ByteBuf): SteerVehicle {
        val sideways = buffer.readFloat()
        val forward = buffer.readFloat()
        val flags = buffer.readUnsignedByte()
        val jump = flags and 1 > 0
        val dismount = flags and 2 > 0

        return SteerVehicle(sideways, forward, jump, dismount)
    }

}

data class SteerVehicle(val sideways: Float, val forward: Float, val jump: Boolean,
                        val dismount: Boolean) : PacketMessage