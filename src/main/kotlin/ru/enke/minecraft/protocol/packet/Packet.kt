package ru.enke.minecraft.protocol.packet

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.data.game.BlockState
import ru.enke.minecraft.protocol.packet.data.game.Position
import ru.enke.minecraft.protocol.packet.data.game.Slot
import java.util.*

interface PacketMessage

interface Packet<T : PacketMessage> {
    fun write(message: T, buffer: ByteBuf)
    fun read(buffer: ByteBuf): T
}

fun ByteBuf.writeVarInt(value: Int) {
    var result = value

    while((result and 0xFFFFFF80.toInt()) != 0x0) {
        writeByte(result or 0x80)
        result = result ushr 7
    }

    writeByte(result)
}

fun ByteBuf.writeVarLong(value: Long) {
    var result = value

    while((result and 0xFFFFFF80).toInt() != 0x0) {
        writeByte((result or 0x80).toInt())
        result = result ushr 7
    }

    writeByte(result.toInt())
}

fun ByteBuf.readVarInt(): Int {
    var result = 0
    var shift = 0
    var b: Int

    do {
        if(shift >= 32) {
            throw IndexOutOfBoundsException("VarInt too long")
        }

        b = readByte().toInt()
        result = result or (b and 0x7F shl shift)
        shift += 7
    } while(b and 0x80 != 0)

    return result
}

fun ByteBuf.readVarLong(): Long {
    var result: Long = 0
    var shift = 0
    var b: Int

    do {
        if(shift >= 64) {
            throw IndexOutOfBoundsException("VarLong too long")
        }

        b = readByte().toInt()
        result = result or (b and 0x7F shl shift).toLong()
        shift += 7
    } while(b and 0x80 != 0)

    return result
}

fun ByteBuf.writeString(value: String) {
    val bytes = value.toByteArray()
    writeVarInt(bytes.size)
    writeBytes(bytes)
}

fun ByteBuf.readString(): String {
    return String(readByteArray())
}

fun ByteBuf.writeByteArray(bytes: ByteArray) {
    writeVarInt(bytes.size)
    writeBytes(bytes)
}

fun ByteBuf.readByteArray(): ByteArray {
    val bytes = ByteArray(readVarInt())
    readBytes(bytes)
    return bytes
}

fun ByteBuf.writeEnum(enum: Enum<*>) {
    writeByte(enum.ordinal)
}

inline fun <reified T : Enum<T>> ByteBuf.readEnum(): T {
    val enums = enumValues<T>()
    val index = readUnsignedByte().toInt()

    if(index > enums.size) {
        throw IllegalArgumentException()
    }

    return enums[index]
}

fun ByteBuf.writeVarEnum(enum: Enum<*>) {
    writeVarInt(enum.ordinal)
}

inline fun <reified T : Enum<T>> ByteBuf.readVarEnum(): T {
    val enums = enumValues<T>()
    val index = readVarInt()

    if(index < 0) {
        throw IllegalArgumentException()
    }

    if(index > enums.size) {
        throw IllegalArgumentException()
    }

    return enums[index]
}

fun ByteBuf.writeEnumAsString(enum: Enum<*>) {
    writeString(enum.name.toLowerCase())
}

inline fun <reified T : Enum<T>> ByteBuf.readEnumAsString(): T {
    return enumValueOf(readString().toUpperCase())
}

fun ByteBuf.writePosition(position: Position) {
    val x = (position.x and 0x3FFFFFF).toLong()
    val y = (position.y and 0xFFF).toLong()
    val z = (position.z and 0x3FFFFFF).toLong()

    writeLong(x shl 38 or (y shl 26) or z)
}

fun ByteBuf.readPosition() : Position {
    val value = readLong()

    val x = (value shr 38).toInt()
    val y = (value shr 26 and 4095).toInt()
    val z = (value shl 38 shr 38).toInt()

    return Position(x, y, z)
}

fun ByteBuf.writeSlot(slot: Slot?) {
    if(slot == null) {
        writeShort(-1)
        return
    }

    writeShort(slot.id)
    writeByte(slot.quantity)
    writeShort(slot.metadata)
    writeBytes(slot.state ?: return)
}

fun ByteBuf.readSlot() : Slot? {
    val id = readShort().toInt()

    if(id == -1) {
        return null
    }

    val quantity = readByte().toInt()
    val metadata = readShort().toInt()

    val length = readableBytes()
    val state = if(length > 0) ByteArray(length) else null

    if(state != null) {
        readBytes(state)
    }

    return Slot(id, quantity, metadata, state)
}

fun ByteBuf.writeUUID(uuid: UUID) {
    writeLong(uuid.mostSignificantBits)
    writeLong(uuid.leastSignificantBits)
}

fun ByteBuf.readUUID(): UUID {
    return UUID(readLong(), readLong())
}

fun ByteBuf.writeBlockState(state: BlockState) {
    writeVarInt(state.id shl 4 or state.data)
}

fun ByteBuf.readBlockState(): BlockState {
    val id = readVarInt()

    return BlockState(id shr 4, id and 0xF)
}

fun ByteBuf.readBytes(): ByteArray {
    val bytes = ByteArray(readableBytes())
    readBytes(bytes)

    return bytes
}
