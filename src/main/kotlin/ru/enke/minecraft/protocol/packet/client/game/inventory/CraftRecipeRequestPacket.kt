package ru.enke.minecraft.protocol.packet.client.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object CraftRecipeRequestPacket : Packet<CraftRecipeRequest> {

    override fun write(message: CraftRecipeRequest, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeVarInt(message.recipeId)
        buffer.writeBoolean(message.makeAll)
    }

    override fun read(buffer: ByteBuf): CraftRecipeRequest {
        val windowId = buffer.readByte().toInt()
        val recipeId = buffer.readVarInt()
        val makeAll = buffer.readBoolean()

        return CraftRecipeRequest(windowId, recipeId, makeAll)
    }

}

data class CraftRecipeRequest(val windowId: Int, val recipeId: Int, val makeAll: Boolean) : PacketMessage