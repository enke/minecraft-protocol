package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object TeleportConfirmPacket : Packet<TeleportConfirm> {

    override fun write(message: TeleportConfirm, buffer: ByteBuf) {
        buffer.writeVarInt(message.teleportId)
    }

    override fun read(buffer: ByteBuf): TeleportConfirm {
        return TeleportConfirm(buffer.readVarInt())
    }

}

data class TeleportConfirm(val teleportId: Int) : PacketMessage
