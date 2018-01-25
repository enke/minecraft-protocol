package ru.enke.minecraft.protocol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import ru.enke.minecraft.protocol.ProtocolState.STATUS
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake
import ru.enke.minecraft.protocol.packet.client.handshake.HandshakePacket
import ru.enke.minecraft.protocol.packet.client.status.StatusRequest

class SimpleProtocolTest {

    companion object {
        private val PACKET_MESSAGE_CLASS = Handshake::class
        private val PACKET_DIRECTION = OUTBOUND
        private val PACKET = HandshakePacket
        private val PACKET_ID: Byte = 0x01
    }

    private val protocol = ru.enke.minecraft.protocol.SimpleProtocol()

    @Before
    fun setUp() {
        protocol.registerPacket(PACKET_DIRECTION, PACKET_ID, PACKET, PACKET_MESSAGE_CLASS)
    }

    @Test
    fun testPacketByMessage() {
        assertEquals(PACKET, protocol.getPacketByMessage(PACKET_DIRECTION, Handshake(0, "hostname", 3306, STATUS)))
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun testUnknownPacketByMessage() {
        assertNull(protocol.getPacketByMessage(PACKET_DIRECTION, StatusRequest()))
    }

    @Test
    fun testPacketById() {
        assertEquals(PACKET, protocol.getPacketById(PACKET_DIRECTION, PACKET_ID))
    }

    @Test
    fun testIdByPacket() {
        assertEquals(PACKET_ID, protocol.getIdByPacket(PACKET_DIRECTION, PACKET))
    }

}
