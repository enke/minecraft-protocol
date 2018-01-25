package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Hand
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object SwingArmPacket : Packet<SwingArm> {

    override fun write(message: SwingArm, buffer: ByteBuf) {
        buffer.writeEnum(message.hand)
    }

    override fun read(buffer: ByteBuf): SwingArm {
        return SwingArm(buffer.readEnum<Hand>())
    }

}

data class SwingArm(val hand: Hand) : PacketMessage
