package ru.enke.minecraft.protocol.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import java.nio.ByteBuffer
import javax.crypto.Cipher
import javax.crypto.Cipher.*
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptionCodec(sharedSecret: SecretKey) : MessageToMessageCodec<ByteBuf, ByteBuf>() {

    private val encode: Cipher
    private val decode: Cipher

    init {
        encode = getCipher(ENCRYPT_MODE, sharedSecret)
        decode = getCipher(DECRYPT_MODE, sharedSecret)
    }

    fun getCipher(mode: Int, sharedSecret: SecretKey): Cipher {
        val cipher = getInstance("AES/CFB8/NoPadding")
        cipher.init(mode, sharedSecret, IvParameterSpec(sharedSecret.encoded))
        return cipher
    }

    override fun encode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        encrypt(encode, buf, out)
    }

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        encrypt(decode, buf, out)
    }

    fun encrypt(cipher: Cipher, buf: ByteBuf, out: MutableList<Any>) {
        val outBuffer = ByteBuffer.allocate(buf.readableBytes())
        cipher.update(buf.nioBuffer(), outBuffer)
        outBuffer.flip()
        out.add(Unpooled.wrappedBuffer(outBuffer))
    }

}
