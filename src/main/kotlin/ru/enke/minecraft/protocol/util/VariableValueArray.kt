package ru.enke.minecraft.protocol.util

class VariableValueArray(private val bitsPerValue: Int, private val capacity: Int) {
    private val backing: LongArray
    private val largestPossibleValue: Long

    init{
        if(capacity < 0) throw IllegalArgumentException(String.format("capacity (%s) must not be negative", capacity))
        if(bitsPerValue < 1) throw IllegalArgumentException(String.format("bitsPerValue (%s) must not be less than 1", bitsPerValue))
        if(bitsPerValue > 64) throw IllegalArgumentException(String.format("bitsPerValue (%s) must not be greater than 64", bitsPerValue))
        backing = LongArray(Math.ceil(bitsPerValue * capacity / 64.0).toInt())
        largestPossibleValue = (1L shl bitsPerValue) - 1L
    }

    fun get(index: Int): Int {
        var index = index
        checkIndex(index)

        index *= bitsPerValue
        var i0 = index shr 6
        val i1 = index and 0x3f

        var value = backing[i0].ushr(i1)
        val i2 = i1 + bitsPerValue

        if(i2 > 64) {
            value = value or (backing[++i0] shl 64 - i1)
        }

        return (value and largestPossibleValue).toInt()
    }

    fun set(index: Int, value: Int) {
        var index = index
        checkIndex(index)

        if(value < 0) throw IllegalArgumentException(String.format("value (%s) must not be negative", value))
        if(value > largestPossibleValue) throw IllegalArgumentException(String.format("value (%s) must not be greater than %s", value, largestPossibleValue))

        index *= bitsPerValue
        var i0 = index shr 6
        val i1 = (index and 0x3f)

        backing[i0] = this.backing[i0] and (this.largestPossibleValue shl i1).inv() or ((value and largestPossibleValue.toInt() shl i1).toLong())
        val i2 = i1 + bitsPerValue

        if(i2 > 64) {
            i0++
            backing[i0] = backing[i0] and ((1L shl i2 - 64) - 1L).inv() or ((value shr 64 - i1).toLong())
        }
    }

    private fun checkIndex(index:Int) {
        if(index < 0) throw IndexOutOfBoundsException(String.format("index (%s) must not be negative", index))
        if(index >= capacity) throw IndexOutOfBoundsException(String.format("index (%s) must not be greater than the capacity (%s)", index, capacity))
    }

    fun increaseBitsPerValueTo(newBitsPerValue:Int):VariableValueArray {
        if(newBitsPerValue < bitsPerValue) {
            throw IllegalArgumentException("Cannot decrease bits per value! (was " + this.bitsPerValue + ", new size " + newBitsPerValue + ")")
        }
        else if (newBitsPerValue == bitsPerValue) {
            throw IllegalArgumentException("Cannot resize to the same size! (size was $newBitsPerValue)")
        }

        val returned = VariableValueArray(newBitsPerValue, capacity)

        for(i in 0 until capacity) {
            returned.set(i, get(i))
        }

        return returned
    }

}