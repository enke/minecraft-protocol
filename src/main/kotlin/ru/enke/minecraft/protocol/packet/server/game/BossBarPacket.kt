package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.BossBarAction
import ru.enke.minecraft.protocol.packet.data.game.BossBarAction.*
import ru.enke.minecraft.protocol.packet.data.game.BossBarColor
import ru.enke.minecraft.protocol.packet.data.game.BossBarDivision
import ru.enke.minecraft.protocol.packet.data.message.Message
import java.util.*
import kotlin.experimental.and

object BossBarPacket : Packet<BossBar> {

    override fun write(message: BossBar, buffer: ByteBuf) {
        val action = message.action

        buffer.writeUUID(message.uuid)
        buffer.writeVarEnum(action)

        if(action == ADD || action == UPDATE_TITLE) {
           buffer.writeString(message.title!!.toJson())
        }

        if(action == ADD || action == UPDATE_HEALTH) {
            buffer.writeFloat(message.health)
        }

        if(action == ADD || action == UPDATE_STYLE) {
            buffer.writeVarEnum(message.color!!)
            buffer.writeVarEnum(message.division!!)
        }

        if(action == ADD || action == UPDATE_FLAGS) {
            var flags = 0

            if(message.darkenSky) {
                flags = flags or 0x1
            }

            if(message.dragonBar) {
                flags = flags or 0x2
            }

            buffer.writeByte(flags)
        }
    }

    override fun read(buffer: ByteBuf): BossBar {
        val uuid = buffer.readUUID()
        val action = buffer.readVarEnum<BossBarAction>()

        var title: Message? = null
        var health: Float = 0F
        var color: BossBarColor? = null
        var division: BossBarDivision? = null
        var darkenSky: Boolean = false
        var dragonBar: Boolean = false

        if(action == ADD || action == UPDATE_TITLE) {
            title = Message.fromJson(buffer.readString())
        }

        if(action == ADD || action == UPDATE_HEALTH) {
            health = buffer.readFloat()
        }

        if(action == ADD || action == UPDATE_STYLE) {
            color = buffer.readVarEnum<BossBarColor>()
            division = buffer.readVarEnum<BossBarDivision>()
        }

        if(action == ADD || action == UPDATE_FLAGS) {
            val flags = buffer.readUnsignedByte()

            darkenSky = (flags and 0x1).toInt() == 0x1
            dragonBar = (flags and 0x2).toInt() == 0x2
        }

        return BossBar(uuid, action, title, health, color, division, darkenSky, dragonBar)
    }

}

data class BossBar(val uuid: UUID, val action: BossBarAction, val title: Message? = null, val health: Float,
                   val color: BossBarColor?, val division: BossBarDivision?, val darkenSky: Boolean = false,
                   val dragonBar: Boolean = false) : PacketMessage
