package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object TabCompleteRequestPacket : Packet<TabCompleteRequest> {

    override fun write(message: TabCompleteRequest, buffer: ByteBuf) {
        buffer.writeString(message.text)
        buffer.writeBoolean(message.assumeCommand)
        buffer.writeBoolean(message.position != null)
        buffer.writePosition(message.position ?: return)
    }

    override fun read(buffer: ByteBuf): TabCompleteRequest {
        val text = buffer.readString()
        val assumeCommand = buffer.readBoolean()
        val position = if(buffer.readBoolean()) buffer.readPosition() else null

        return TabCompleteRequest(text, assumeCommand, position)
    }

}

data class TabCompleteRequest(val text: String, val assumeCommand: Boolean, val position: Position?) : PacketMessage
