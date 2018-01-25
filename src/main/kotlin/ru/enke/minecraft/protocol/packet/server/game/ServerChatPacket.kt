package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.data.message.MessageType

object ServerChatPacket : Packet<ServerChat> {

    override fun write(message: ServerChat, buffer: ByteBuf) {
        buffer.writeString(message.message.toJson())
        buffer.writeEnum(message.type)
    }

    override fun read(buffer: ByteBuf): ServerChat {
        val message = Message.fromJson(buffer.readString())
        val type = buffer.readEnum<MessageType>()

        return ServerChat(message, type)
    }

}

data class ServerChat(val message: Message, val type: MessageType) : PacketMessage
