package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.PaintingType
import ru.enke.minecraft.protocol.packet.data.game.Position
import java.util.*

object SpawnPaintingPacket : Packet<SpawnPainting> {

    override fun write(message: SpawnPainting, buffer: ByteBuf) {
        buffer.writeVarInt(message.id)
        buffer.writeUUID(message.uuid)
        buffer.writeEnumAsString(message.type)
        buffer.writePosition(message.position)
        buffer.writeByte(message.direction)
    }

    override fun read(buffer: ByteBuf): SpawnPainting {
        val id = buffer.readVarInt()
        val uuid = buffer.readUUID()
        val type = buffer.readEnumAsString<PaintingType>()
        val position = buffer.readPosition()
        val direction = buffer.readUnsignedByte().toInt()

        return SpawnPainting(id, uuid, type, position, direction)
    }

}

data class SpawnPainting(val id: Int,
                         val uuid: UUID,
                         val type: PaintingType,
                         val position: Position,
                         val direction: Int) : PacketMessage
