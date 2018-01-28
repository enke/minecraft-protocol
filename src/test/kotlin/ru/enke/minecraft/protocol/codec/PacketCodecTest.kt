package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.codec.DecoderException
import org.junit.Assert.*
import org.junit.Test
import ru.enke.minecraft.protocol.Protocol
import ru.enke.minecraft.protocol.ProtocolSide.CLIENT
import ru.enke.minecraft.protocol.ProtocolSide.SERVER
import ru.enke.minecraft.protocol.ProtocolState
import ru.enke.minecraft.protocol.ProtocolState.STATUS
import ru.enke.minecraft.protocol.ServerGameProtocol
import ru.enke.minecraft.protocol.codec.FilterMode.ACCEPT
import ru.enke.minecraft.protocol.codec.FilterMode.IGNORE
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.client.game.SwingArm
import ru.enke.minecraft.protocol.packet.client.game.SwingArmPacket
import ru.enke.minecraft.protocol.packet.client.game.TeleportConfirm
import ru.enke.minecraft.protocol.packet.client.game.TeleportConfirmPacket
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake
import ru.enke.minecraft.protocol.packet.client.handshake.HandshakePacket
import ru.enke.minecraft.protocol.packet.data.game.Hand

class PacketCodecTest {

    companion object {
        val handshake = Handshake(Protocol.VERSION, "localhost", 25565, STATUS)
        val teleportConfirm = TeleportConfirm(15)
        val swingArm = SwingArm(Hand.MAIN_HAND)
    }

    @Test
    fun testEncode() {
        val channel = EmbeddedChannel(PacketCodec(CLIENT))
        channel.writeOutbound(handshake)

        val buffer = channel.readOutbound<ByteBuf>()

        assertEquals(0x00, buffer.readVarInt())
        assertEquals(Protocol.VERSION, buffer.readVarInt())
        assertEquals("localhost", buffer.readString())
        assertEquals(25565, buffer.readShort().toInt())
        assertEquals(STATUS, buffer.readVarEnum<ProtocolState>())
    }

    @Test
    fun testDecode() {
        val channel = EmbeddedChannel(PacketCodec(SERVER))
        val buffer = Unpooled.buffer()

        buffer.writeVarInt(0x00)
        HandshakePacket.write(handshake, buffer)
        channel.writeInbound(buffer)

        val inboundHandshake = channel.readInbound<PacketMessage>() as Handshake
        assertEquals(Protocol.VERSION, inboundHandshake.protocol)
        assertEquals("localhost", inboundHandshake.hostname)
        assertEquals(25565, inboundHandshake.port)
        assertEquals(STATUS, inboundHandshake.state)
    }

    @Test(expected = DecoderException::class)
    fun testDecodeUnknownPacket() {
        val channel = EmbeddedChannel(PacketCodec(CLIENT))
        val buffer = Unpooled.buffer()
        buffer.writeVarInt(0x02)
        channel.writeInbound(buffer)
    }

    @Test
    fun testDecodeFilterAcceptModeSkip() {
        val packetCodec = createCodecWithFilter(ACCEPT, SwingArmPacket)
        val channel = EmbeddedChannel(packetCodec)
        val buffer = Unpooled.buffer()

        buffer.writeVarInt(0x1D)
        SwingArmPacket.write(swingArm, buffer)
        channel.writeInbound(buffer)

        assertTrue(channel.readInbound<PacketMessage>() is SwingArm)
    }

    @Test
    fun testDecodeFilterAcceptModeDiscard() {
        val packetCodec = createCodecWithFilter(ACCEPT, SwingArmPacket)
        val channel = EmbeddedChannel(packetCodec)
        val buffer = Unpooled.buffer()

        buffer.writeVarInt(0x00)
        TeleportConfirmPacket.write(teleportConfirm, buffer)
        channel.writeInbound(buffer)

        assertNull(channel.readInbound<PacketMessage>())
    }

    @Test
    fun testDecodeFilterIgnoreModeSkip() {
        val packetCodec = createCodecWithFilter(IGNORE, SwingArmPacket)
        val channel = EmbeddedChannel(packetCodec)
        val buffer = Unpooled.buffer()

        buffer.writeVarInt(0x00)
        TeleportConfirmPacket.write(teleportConfirm, buffer)
        channel.writeInbound(buffer)

        assertTrue(channel.readInbound<PacketMessage>() is TeleportConfirm)
    }

    @Test
    fun testDecodeFilterIgnoreModeDiscard() {
        val packetCodec = createCodecWithFilter(IGNORE, SwingArmPacket)
        val channel = EmbeddedChannel(packetCodec)
        val buffer = Unpooled.buffer()

        buffer.writeVarInt(0x1D)
        SwingArmPacket.write(swingArm, buffer)
        channel.writeInbound(buffer)

        assertNull(channel.readInbound<PacketMessage>())
    }

    private fun createCodecWithFilter(mode: FilterMode, packet: Packet<*>): PacketCodec {
        val filter = Filter(mode)
        filter.add(packet)

        val packetCodec = PacketCodec(SERVER, filter = filter)
        packetCodec.protocol = ServerGameProtocol

        return packetCodec
    }

}
