package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Status
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object ClientStatusPacket : Packet<ClientStatus> {

    override fun write(message: ClientStatus, buffer: ByteBuf) {
        buffer.writeEnum(message.status)
    }

    override fun read(buffer: ByteBuf): ClientStatus {
        return ClientStatus(buffer.readEnum<Status>())
    }

}

data class ClientStatus(val status: Status) : PacketMessage

