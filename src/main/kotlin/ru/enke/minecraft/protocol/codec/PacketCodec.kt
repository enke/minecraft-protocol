package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import ru.enke.minecraft.protocol.ProtocolState.HANDSHAKE
import ru.enke.minecraft.protocol.codec.FilterMode.ACCEPT
import ru.enke.minecraft.protocol.codec.FilterMode.IGNORE
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake
import ru.enke.minecraft.protocol.packet.readVarInt
import ru.enke.minecraft.protocol.packet.server.login.LoginSuccess
import ru.enke.minecraft.protocol.packet.writeVarInt

class PacketCodec(val side: ru.enke.minecraft.protocol.ProtocolSide,
                  val ignoreMissingPackets: Boolean = false,
                  val ignoreUnreadBuffer: Boolean = false,
                  val filter: ru.enke.minecraft.protocol.codec.Filter? = null) : ByteToMessageCodec<PacketMessage>() {

    internal var protocol = HANDSHAKE.getProtocolBySide(side)

    override fun encode(ctx: ChannelHandlerContext, msg: PacketMessage, out: ByteBuf) {
        val packet = protocol.getPacketByMessage(OUTBOUND, msg)
        val packetId = protocol.getIdByPacket(OUTBOUND, packet)

        out.writeVarInt(packetId.toInt())
        packet.write(msg, out)
        handleProtocol(msg)
    }

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val packetId = buf.readVarInt().toByte()
        val totalBytes = buf.readableBytes()
        val packet = protocol.getPacketById(INBOUND, packetId)

        if(packet == null) {
            if(ignoreMissingPackets) {
                buf.skipBytes(buf.readableBytes())
                return
            }

            throw IllegalStateException("Unknown packet $packetId")
        }

        if(filter != null) {
            val contains = filter.contains(packet)
            val mode = filter.mode

            if((mode == ACCEPT && !contains) || (mode == IGNORE && contains)) {
                buf.skipBytes(buf.readableBytes())
                return
            }
        }

        val msg = packet.read(buf)
        handleProtocol(msg)
        out.add(msg)

        val unreadBytes = buf.readableBytes()

        if(unreadBytes > 0) {
            if(ignoreUnreadBuffer) {
                buf.skipBytes(buf.readableBytes())
                return
            }

            throw IllegalStateException("Unread $unreadBytes/$totalBytes bytes in $msg")
        }
    }

    private fun handleProtocol(msg: PacketMessage) {
        when(msg) {
            is Handshake -> switchProtocol(msg.state)
            is LoginSuccess -> switchProtocol(ru.enke.minecraft.protocol.ProtocolState.GAME)
        }
    }

    private fun switchProtocol(state: ru.enke.minecraft.protocol.ProtocolState) {
        protocol = state.getProtocolBySide(side)
    }

}

// Позволяет отсеивать ненужные пакеты перед их чтением.
class Filter(val mode: ru.enke.minecraft.protocol.codec.FilterMode) : ArrayList<Packet<*>>()

// Режимы фильтрации пакетов.
enum class FilterMode {
    // Прнимаем только пакеты, который указанны.
    ACCEPT,
    // Принимаем все пакеты, кроме указанных.
    IGNORE
}
