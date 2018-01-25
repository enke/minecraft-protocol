package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object ClientPluginMessagePacket : Packet<ClientPluginMessage> {

    override fun write(message: ClientPluginMessage, buffer: ByteBuf) {
        buffer.writeString(message.channel)
        buffer.writeBytes(message.data)
    }
    override fun read(buffer: ByteBuf): ClientPluginMessage {
        val channel = buffer.readString()
        val data = buffer.readBytes()

        return ClientPluginMessage(channel, data)
    }

}

data class ClientPluginMessage(val channel: String, val data: ByteArray) : PacketMessage
