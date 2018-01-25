package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.ResourcePackResult
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object ResourcePackStatusPacket : Packet<ResourcePackStatus> {

    override fun write(message: ResourcePackStatus, buffer: ByteBuf) {
        buffer.writeEnum(message.result)
    }

    override fun read(buffer: ByteBuf): ResourcePackStatus {
        return ResourcePackStatus(buffer.readEnum<ResourcePackResult>())
    }

}

data class ResourcePackStatus(val result: ResourcePackResult) : PacketMessage
