package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Hand
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object UseItemPacket : Packet<UseItem> {

    override fun write(message: UseItem, buffer: ByteBuf) {
        buffer.writeEnum(message.hand)
    }

    override fun read(buffer: ByteBuf): UseItem {
        return UseItem(buffer.readEnum<Hand>())
    }

}

data class UseItem(val hand: Hand) : PacketMessage
