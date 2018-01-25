package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Position
import ru.enke.minecraft.protocol.packet.readPosition
import ru.enke.minecraft.protocol.packet.writePosition

object SpawnPositionPacket : Packet<SpawnPosition> {

    override fun write(message: SpawnPosition, buffer: ByteBuf) {
         buffer.writePosition(message.position)
    }

    override fun read(buffer: ByteBuf): SpawnPosition {
        return SpawnPosition(buffer.readPosition())
    }

}

data class SpawnPosition(val position: Position) : PacketMessage
