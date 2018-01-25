package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.WorldBorderAction
import ru.enke.minecraft.protocol.packet.data.game.WorldBorderAction.*

object WorldBorderPacket : Packet<WorldBorder> {

    override fun write(message: WorldBorder, buffer: ByteBuf) {
        val action = message.action
        buffer.writeVarEnum(action)

        if(action == SET_SIZE) {
            buffer.writeDouble(message.radius)
        }

        if(action == SET_CENTER || action == INITIALIZE) {
            buffer.writeDouble(message.centerX)
            buffer.writeDouble(message.centerZ)
        }

        if(action == LERP_SIZE || action == INITIALIZE) {
            buffer.writeDouble(message.oldRadius)
            buffer.writeDouble(message.newRadius)
            buffer.writeVarLong(message.speed)
        }

        if(action == INITIALIZE) {
            buffer.writeVarInt(message.portalTeleportBoundary)
        }

        if(action == SET_WARNING_TIME || action == INITIALIZE) {
            buffer.writeVarInt(message.warningTime)
        }

        if(action == SET_WARNING_BLOCKS || action == INITIALIZE) {
            buffer.writeVarInt(message.warningBlocks)
        }
    }

    override fun read(buffer: ByteBuf): WorldBorder {
        val action = buffer.readVarEnum<WorldBorderAction>()
        var radius: Double = 0.0
        var oldRadius: Double = 0.0
        var newRadius: Double = 0.0
        var speed: Long = 0
        var centerX: Double = 0.0
        var centerZ: Double = 0.0
        var portalTeleportBoundary: Int = 0
        var warningTime: Int = 0
        var warningBlocks: Int = 0

        if(action == SET_SIZE) {
            radius = buffer.readDouble()
        }

        if(action == SET_CENTER || action == INITIALIZE) {
            centerX = buffer.readDouble()
            centerZ = buffer.readDouble()
        }

        if(action == LERP_SIZE || action == INITIALIZE) {
            oldRadius = buffer.readDouble()
            newRadius = buffer.readDouble()
            speed = buffer.readVarLong()
        }

        if(action == INITIALIZE) {
            portalTeleportBoundary = buffer.readVarInt()
        }

        if(action == SET_WARNING_TIME || action == INITIALIZE) {
            warningTime = buffer.readVarInt()
        }

        if(action == SET_WARNING_BLOCKS || action == INITIALIZE) {
            warningBlocks = buffer.readVarInt()
        }

        return WorldBorder(action, radius, oldRadius, newRadius, speed, centerX, centerZ,
                portalTeleportBoundary, warningTime, warningBlocks)
    }

}

data class WorldBorder(val action: WorldBorderAction, val radius: Double, val oldRadius: Double,
                       val newRadius: Double, val speed: Long, val centerX: Double, val centerZ: Double,
                       val portalTeleportBoundary: Int, val warningTime: Int, val warningBlocks: Int) : PacketMessage
