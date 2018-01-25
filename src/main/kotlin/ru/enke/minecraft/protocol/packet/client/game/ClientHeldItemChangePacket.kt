package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ClientItemHeldChangePacket : Packet<ClientItemHeldChange> {

    override fun write(message: ClientItemHeldChange, buffer: ByteBuf) {
        buffer.writeShort(message.slot)
    }

    override fun read(buffer: ByteBuf): ClientItemHeldChange {
        return ClientItemHeldChange(buffer.readShort().toInt())
    }

}

data class ClientItemHeldChange(val slot: Int) : PacketMessage