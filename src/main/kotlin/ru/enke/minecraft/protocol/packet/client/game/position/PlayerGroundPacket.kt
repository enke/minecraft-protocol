package ru.enke.minecraft.protocol.packet.client.game.position

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object PlayerGroundPacket : Packet<PlayerGround> {

    override fun write(message: PlayerGround, buffer: ByteBuf) {
        buffer.writeBoolean(message.ground)
    }

    override fun read(buffer: ByteBuf): PlayerGround {
        return PlayerGround(buffer.readBoolean())
    }

}

data class PlayerGround(val ground: Boolean) : PacketMessage