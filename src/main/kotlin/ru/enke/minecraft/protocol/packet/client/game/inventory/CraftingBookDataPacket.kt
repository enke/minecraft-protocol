package ru.enke.minecraft.protocol.packet.client.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.CraftingBookDataType
import ru.enke.minecraft.protocol.packet.data.game.CraftingBookDataType.CRAFTING_BOOK_STATUS
import ru.enke.minecraft.protocol.packet.data.game.CraftingBookDataType.DISPLAYED_RECIPE
import ru.enke.minecraft.protocol.packet.readVarEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object CraftingBookDataPacket : Packet<CraftingBookData> {

    override fun write(message: CraftingBookData, buffer: ByteBuf) {
        buffer.writeEnum(message.type)

        when(message.type) {
            DISPLAYED_RECIPE -> buffer.writeInt(message.recipeId)
            CRAFTING_BOOK_STATUS -> {
                buffer.writeBoolean(message.craftBookOpen)
                buffer.writeBoolean(message.filterActive)
            }
        }
    }

    override fun read(buffer: ByteBuf): CraftingBookData {
        val type = buffer.readVarEnum<CraftingBookDataType>()
        val recipeId = if(type == DISPLAYED_RECIPE) buffer.readInt() else 0
        val craftBookOpen = if(type == CRAFTING_BOOK_STATUS) buffer.readBoolean() else false
        val filterActive = if(type == CRAFTING_BOOK_STATUS) buffer.readBoolean() else false

        return CraftingBookData(type, recipeId, craftBookOpen, filterActive)
    }

}

data class CraftingBookData(val type: CraftingBookDataType,
                            val recipeId: Int = 0,
                            val craftBookOpen: Boolean = false,
                            val filterActive: Boolean = false) : PacketMessage
