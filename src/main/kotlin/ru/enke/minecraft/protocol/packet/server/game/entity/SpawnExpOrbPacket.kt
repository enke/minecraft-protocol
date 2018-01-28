package ru.enke.minecraft.protocol.packet.server.game.entity

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object SpawnExpOrbPacket : Packet<SpawnExpOrb> {

    override fun write(message: SpawnExpOrb, buffer: ByteBuf) {
        buffer.writeVarInt(message.entityId)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeShort(message.amount)
    }

    override fun read(buffer: ByteBuf): SpawnExpOrb {
        val entityId = buffer.readVarInt()
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val amount = buffer.readShort().toInt()

        return SpawnExpOrb(entityId, x, y, z, amount)
    }

}

data class SpawnExpOrb(val entityId: Int, val x: Double, val y: Double, val z: Double, val amount: Int) : PacketMessage