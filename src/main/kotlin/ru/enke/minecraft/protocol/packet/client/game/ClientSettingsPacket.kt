package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.ChatMode
import ru.enke.minecraft.protocol.packet.data.game.MainHand
import ru.enke.minecraft.protocol.packet.data.game.SkinPart
import kotlin.experimental.and


object ClientSettingsPacket : Packet<ClientSettings> {

    override fun write(message: ClientSettings, buffer: ByteBuf) {
        buffer.writeString(message.locale)
        buffer.writeByte(message.renderDistance)
        buffer.writeEnum(message.chatMode)
        buffer.writeBoolean(message.colors)

        var flags = 0

        for(part in message.visibleParts) {
            flags = flags or (1 shl part.ordinal)
        }

        buffer.writeByte(flags)
        buffer.writeEnum(message.mainHand)
    }

    override fun read(buffer: ByteBuf): ClientSettings {
        val locale = buffer.readString()
        val renderDistance = buffer.readByte().toInt()
        val chatMode = buffer.readEnum<ChatMode>()
        val colors = buffer.readBoolean()

        val visibleParts = mutableListOf<SkinPart>()
        val flags = buffer.readUnsignedByte()

        for(part in SkinPart.values()) {
            val bit = (1 shl part.ordinal).toShort()

            if((flags and bit) == bit) {
                visibleParts.add(part)
            }
        }

        val mainHand = buffer.readEnum<MainHand>()

        return ClientSettings(locale, renderDistance, chatMode, colors, visibleParts, mainHand)
    }

}

data class ClientSettings(val locale: String, val renderDistance: Int, val chatMode: ChatMode, val colors: Boolean,
                          val visibleParts: List<SkinPart>, val mainHand: MainHand) : PacketMessage