package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readUUID
import ru.enke.minecraft.protocol.packet.writeUUID
import java.util.*

object SpectatePacket : Packet<Spectate> {

    override fun write(message: Spectate, buffer: ByteBuf) {
        buffer.writeUUID(message.uuid)
    }

    override fun read(buffer: ByteBuf): Spectate {
        return Spectate(buffer.readUUID())
    }

}

data class Spectate(val uuid: UUID) : PacketMessage
