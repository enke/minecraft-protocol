package ru.enke.minecraft.protocol.packet.server.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.writeVarInt

object LoginSetCompressionPacket : Packet<LoginSetCompression> {

    override fun write(message: LoginSetCompression, buffer: ByteBuf) {
        buffer.writeVarInt(message.threshold)
    }

    override fun read(buffer: ByteBuf): LoginSetCompression {
        return LoginSetCompression(buffer.readVarInt())
    }

}

data class LoginSetCompression(val threshold: Int) : PacketMessage
