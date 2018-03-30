package cc.rainx.ktdx.helper

import kotlin.experimental.and
import java.io.IOException
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.io.ByteArrayOutputStream
import java.util.zip.Inflater



object KtdxHelper {

    fun hexStringToByteArray(source:String):ByteArray {
        val newSource = source.replace(" ", "")

        val ba =  ByteArray(newSource.count() / 2)
        for (i in 0 until ba.count()) {
            ba[i] = ((Character.digit(newSource[i * 2], 16) shl 4) +
                    (Character.digit(newSource[i * 2 + 1], 16))).toByte()
        }
        return ba
    }

    fun byteArrayToHexString(b: ByteArray, withSpace:Boolean = false): String {
        var a = ""
        for (i in b.indices) {
            var hex = Integer.toHexString((b[i].toInt() and 0xFF))
            if (hex.length == 1) {
                hex = '0' + hex
            }

            val space = if (withSpace) " " else ""
            a = a + space + hex
        }

        return a
    }


    /**
     * 解压缩 zlib
     *
     * @param data
     * 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    fun decompress(data: ByteArray): ByteArray {
        var output = ByteArray(0)

        val decompresser = Inflater()
        decompresser.reset()
        decompresser.setInput(data)

        val o = ByteArrayOutputStream(data.size)
        try {
            val buf = ByteArray(1024)
            while (!decompresser.finished()) {
                val i = decompresser.inflate(buf)
                o.write(buf, 0, i)
            }
            output = o.toByteArray()
        } catch (e: Exception) {
            output = data
            e.printStackTrace()
        } finally {
            try {
                o.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        decompresser.end()
        return output
    }
}