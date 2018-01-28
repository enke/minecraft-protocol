package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object SteerBoatPacket : Packet<SteerBoat> {

    override fun write(message: SteerBoat, buffer: ByteBuf) {
        buffer.writeBoolean(message.rightPaddleTurning)
        buffer.writeBoolean(message.leftPaddleTurning)
    }

    override fun read(buffer: ByteBuf): SteerBoat {
        val rightPaddleTurning = buffer.readBoolean()
        val leftPaddleTurning = buffer.readBoolean()

        return SteerBoat(rightPaddleTurning, leftPaddleTurning)
    }

}

data class SteerBoat(val rightPaddleTurning: Boolean, val leftPaddleTurning: Boolean) : PacketMessage