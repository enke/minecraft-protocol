package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import kotlin.experimental.and
import kotlin.experimental.or

object ServerPlayerAbilitiesPacket : Packet<ServerPlayerAbilities> {

    override fun write(message: ServerPlayerAbilities, buffer: ByteBuf) {
        var flags: Byte = 0

        if(message.invincible) {
            flags = flags or 1
        }

        if(message.canFly) {
            flags = flags or 2
        }

        if(message.flying) {
            flags = flags or 4
        }

        if(message.creative) {
            flags = flags or 8
        }

        buffer.writeByte(flags.toInt())
        buffer.writeFloat(message.flySpeed)
        buffer.writeFloat(message.walkSpeed)
    }

    override fun read(buffer: ByteBuf): ServerPlayerAbilities {
        val flags = buffer.readByte()
        val invincible = flags and 1 > 0
        val canFly = flags and 2 > 0
        val flying = flags and 4 > 0
        val creative = flags and 8 > 0
        val flySpeed = buffer.readFloat()
        val walkSpeed = buffer.readFloat()

        return ServerPlayerAbilities(invincible, canFly, flying, creative, flySpeed, walkSpeed)
    }

}

data class ServerPlayerAbilities(val invincible: Boolean, val canFly: Boolean, val flying: Boolean,
                                 val creative: Boolean, val flySpeed: Float, val walkSpeed: Float) : PacketMessage