package ru.enke.minecraft.protocol.packet.client.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Direction
import ru.enke.minecraft.protocol.packet.data.game.Hand
import ru.enke.minecraft.protocol.packet.data.game.Position

object BlockPlacePacket : Packet<BlockPlace> {

    override fun write(message: BlockPlace, buffer: ByteBuf) {
        buffer.writePosition(message.position)
        buffer.writeEnum(message.direction)
        buffer.writeEnum(message.hand)
        buffer.writeFloat(message.cursorX)
        buffer.writeFloat(message.cursorY)
        buffer.writeFloat(message.cursorZ)
    }

    override fun read(buffer: ByteBuf): BlockPlace {
        val position = buffer.readPosition()
        val direction = buffer.readEnum<Direction>()
        val hand = buffer.readEnum<Hand>()
        val cursorX = buffer.readFloat()
        val cursorY = buffer.readFloat()
        val cursorZ = buffer.readFloat()

        return BlockPlace(position, direction, hand, cursorX, cursorY, cursorZ)
    }

}

data class BlockPlace(val position: Position, val direction: Direction, val hand: Hand,
                      val cursorX: Float, val cursorY: Float, val cursorZ: Float) : PacketMessage
