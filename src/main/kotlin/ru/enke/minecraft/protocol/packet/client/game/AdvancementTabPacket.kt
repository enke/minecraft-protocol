package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.AdvancementTabAction
import ru.enke.minecraft.protocol.packet.data.game.AdvancementTabAction.CLOSED_SCREEN
import ru.enke.minecraft.protocol.packet.data.game.AdvancementTabAction.OPENED_TAB

object AdvancementTabPacket : Packet<AdvancementTab> {

    override fun write(message: AdvancementTab, buffer: ByteBuf) {
        buffer.writeVarEnum(message.action)

        if(message.action == OPENED_TAB) {
            buffer.writeString(message.tabId!!)
        }
    }

    override fun read(buffer: ByteBuf): AdvancementTab {
        val action = buffer.readVarEnum<AdvancementTabAction>()

       when(action) {
           CLOSED_SCREEN -> return AdvancementTab(action)
           OPENED_TAB -> return AdvancementTab(action, buffer.readString())
       }
    }

}

data class AdvancementTab(val action: AdvancementTabAction, val tabId: String? = null) : PacketMessage