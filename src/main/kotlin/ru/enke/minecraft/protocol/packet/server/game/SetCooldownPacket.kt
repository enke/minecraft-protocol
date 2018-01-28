package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object SetCooldownPacket : Packet<SetCooldown> {

    override fun write(message: SetCooldown, buffer: ByteBuf) {
        buffer.writeVarInt(message.itemId)
        buffer.writeVarInt(message.cooldownTicks)
    }

    override fun read(buffer: ByteBuf): SetCooldown {
        val itemId = buffer.readVarInt()
        val cooldownTicks = buffer.readVarInt()

        return SetCooldown(itemId, cooldownTicks)
    }

}

data class SetCooldown(val itemId: Int, val cooldownTicks: Int) : PacketMessage
