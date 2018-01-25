package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position
import ru.enke.minecraft.protocol.packet.data.game.TileUpdateType

object TileEntityUpdatePacket : Packet<TileEntityUpdate> {

    override fun write(message: TileEntityUpdate, buffer: ByteBuf) {
        buffer.writePosition(message.position)
        buffer.writeEnum(message.type)
        buffer.writeBytes(message.state)
    }

    override fun read(buffer: ByteBuf): TileEntityUpdate {
        val position = buffer.readPosition()
        val type = buffer.readEnum<TileUpdateType>()
        val state = buffer.readBytes()

        return TileEntityUpdate(position, type, state)
    }

}

data class TileEntityUpdate(val position: Position, val type: TileUpdateType, val state: ByteArray) : PacketMessage
