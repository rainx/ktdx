package cc.rainx.ktdx.parser

import cc.rainx.ktdx.helper.KtdxHelper
import cc.rainx.ktdx.parser.params.GetSecurityListParams
import cc.rainx.ktdx.parser.result.Result
import cc.rainx.ktdx.parser.result.SecurityListEntry
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

class GetSecurityList (socket: Socket?): BaseParser(socket) {

    fun setParams(params: GetSecurityListParams) {
        super.setParams(params)
        val bb = ByteBuffer.allocate(16)
        bb.order(ByteOrder.LITTLE_ENDIAN)
        bb.put(KtdxHelper.hexStringToByteArray("0c 01 18 64 01 01 06 00 06 00 50 04"))
        bb.putShort(params.market.toShort())
        bb.putShort(params.start.toShort())
        sendPkg = bb.array()
    }

    override fun parseResponse(bodyBuf: ByteArray): MutableList<SecurityListEntry>? {
        val buf = ByteBuffer.wrap(bodyBuf)
        buf.order(ByteOrder.LITTLE_ENDIAN)
        val num = buf.getShort().toInt() and 0xffff
        var list : MutableList<SecurityListEntry> = ArrayList<SecurityListEntry>(num)
        var pos = 2

        for (i in 0 until num) {
            val code = bodyBuf.slice(pos until pos + 6).joinToString()
            pos += 6
            buf.position(pos)
            val volUnit = buf.short.toInt() and 0xffff
            pos += 2
            val name = bodyBuf.copyOfRange(pos, pos + 8).toString(Charset.forName("gbk"))
            pos += 8
            // skip 4
            pos += 4
            buf.position(pos)
            val decimalPoint = buf.get().toInt() and 0xff
            pos += 1
            buf.position(pos)
            val preClose = buf.float
            pos += 4
            // skip 4
            pos += 4
            list.add(SecurityListEntry(
                    code = code,
                    volUnit = volUnit,
                    decimalPoint = decimalPoint,
                    name = name,
                    preClose = preClose
            ))

        }

        return list
    }
}