package ru.enke.minecraft.protocol.packet.server.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position
import kotlin.experimental.and

object MultiBlockChangePacket : Packet<MultiBlockChange> {

    override fun write(message: MultiBlockChange, buffer: ByteBuf) {
        val chunkX = message.chunkX
        val chunkZ = message.chunkZ
        val changes = message.changes

        buffer.writeInt(chunkX)
        buffer.writeInt(chunkZ)
        buffer.writeVarInt(message.changes.size)

        for((position, state) in changes) {
            val x = (position.x - (chunkX shl 4)) shl 12
            val y = position.y
            val z = (position.z - (chunkZ shl 4)) shl 8

            buffer.writeShort(x or z or y)
            buffer.writeBlockState(state)
        }
    }

    override fun read(buffer: ByteBuf): MultiBlockChange {
        val chunkX = buffer.readInt()
        val chunkZ = buffer.readInt()
        val length = buffer.readVarInt()
        val changes = mutableListOf<BlockChange>()

        for(i in 0..length - 1) {
            val position = buffer.readShort()
            val state = buffer.readBlockState()

            val x = (chunkX shl 4) + (position.toInt() shr 12 and 15)
            val y = (position and 255).toInt()
            val z = (chunkZ shl 4) + (position.toInt() shr 8 and 15)

            changes.add(BlockChange(Position(x, y, z), state))
        }

        return MultiBlockChange(chunkX, chunkZ, changes)
    }

}

data class MultiBlockChange(val chunkX: Int, val chunkZ: Int, val changes: List<BlockChange>) : PacketMessage
