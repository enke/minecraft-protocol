package ru.enke.minecraft.protocol.packet.client.handshake

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object HandshakePacket : Packet<Handshake> {

    override fun write(message: Handshake, buffer: ByteBuf) {
        buffer.writeVarInt(message.protocol)
        buffer.writeString(message.hostname)
        buffer.writeShort(message.port)
        buffer.writeVarEnum(message.state)
    }

    override fun read(buffer: ByteBuf): Handshake {
        val protocol = buffer.readVarInt()
        val hostname = buffer.readString()
        val port = buffer.readShort().toInt()
        val state = buffer.readVarEnum<ru.enke.minecraft.protocol.ProtocolState>()

        return Handshake(protocol, hostname, port, state)
    }

}

data class Handshake(val protocol: Int, val hostname: String, val port: Int, val state: ru.enke.minecraft.protocol.ProtocolState) : PacketMessage
