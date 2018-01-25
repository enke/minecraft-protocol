package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.TitleAction
import ru.enke.minecraft.protocol.packet.data.game.TitleAction.*
import ru.enke.minecraft.protocol.packet.data.message.Message

object TitlePacket : Packet<Title> {

    override fun write(message: Title, buffer: ByteBuf) {
        val action = message.action
        buffer.writeVarEnum(action)

        if(action == TITLE || action == SUBTITLE || action == ACTION_BAR) {
            buffer.writeString(message.text!!.toJson())
        } else if(action == TIMES) {
            buffer.writeInt(message.fadeIn)
            buffer.writeInt(message.stay)
            buffer.writeInt(message.fadeOut)
        }
    }

    override fun read(buffer: ByteBuf): Title {
        val action = buffer.readVarEnum<TitleAction>()
        var text: Message? = null
        var fadeIn: Int = 0
        var stay: Int = 0
        var fadeOut: Int = 0

        if(action == TITLE || action == SUBTITLE || action == ACTION_BAR) {
            text = Message.fromJson(buffer.readString())
        } else if(action == TIMES) {
            fadeIn = buffer.readInt()
            stay = buffer.readInt()
            fadeOut = buffer.readInt()
        }

        return Title(action, text, fadeIn, stay, fadeOut)
    }

}

data class Title(val action: TitleAction, val text: Message?, val fadeIn: Int, val stay: Int,
                 val fadeOut: Int) : PacketMessage
