package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object ServerPluginMessagePacket : Packet<ServerPluginMessage> {

    override fun write(message: ServerPluginMessage, buffer: ByteBuf) {
        buffer.writeString(message.channel)
        buffer.writeBytes(message.data)
    }

    override fun read(buffer: ByteBuf): ServerPluginMessage {
        val channel = buffer.readString()
        val data = buffer.readBytes()

        return ServerPluginMessage(channel, data)
    }

}

data class ServerPluginMessage(val channel: String, val data: ByteArray) : PacketMessage
