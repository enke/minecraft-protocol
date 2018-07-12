package ru.enke.minecraft.protocol.util

import kotlin.experimental.and

class NibbleArray @JvmOverloads constructor(val data: ByteArray = ByteArray(2048)) {

    fun set(x: Int, y: Int, z: Int, value: Byte) {
        set((y shl 8) or (z shl 4) or x, value)
    }

    fun set(index: Int, value: Byte) {
        val half = index / 2

        val value = (value and 0xf).toInt()
        val previous = data[half].toInt()

        if (index % 2 == 0) {
            data[half] = ((previous and 240) or value).toByte()
        } else {
            data[half] = ((previous and 15) or (value shl 4)).toByte()
        }
    }

    fun get(x: Int, y: Int, z: Int): Byte {
        return get((y shl 8) or (z shl 4) or x)
    }

    fun get(index: Int): Byte {
        val value = data[index / 2].toInt()

        return if(index % 2 == 0) {
            (value and 15).toByte()
        } else {
            ((value and 240) shr 4).toByte()
        }
    }

}