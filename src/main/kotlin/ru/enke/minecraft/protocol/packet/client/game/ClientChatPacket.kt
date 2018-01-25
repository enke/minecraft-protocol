package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object ClientChatPacket : Packet<ClientChat> {

    override fun write(message: ClientChat, buffer: ByteBuf) {
        buffer.writeString(message.text)
    }

    override fun read(buffer: ByteBuf): ClientChat {
        return ClientChat(buffer.readString())
    }

}

data class ClientChat(val text: String) : PacketMessage
