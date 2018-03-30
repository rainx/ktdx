package cc.rainx.ktdx.parser

import cc.rainx.ktdx.helper.KtdxHelper
import java.net.Socket

class SetupCmd1(socket: Socket?): BaseParser(socket) {
    override fun setup()  {
        sendPkg = KtdxHelper.hexStringToByteArray("0c 02 18 93 00 01 03 00 03 00 0d 00 01")
    }

    override fun parseResponse(bodyBuf: ByteArray): Any? {
        return super.parseResponse(bodyBuf)
    }
}

class SetupCmd2(socket: Socket?): BaseParser(socket) {
    override fun setup()  {
        sendPkg = KtdxHelper.hexStringToByteArray("0c 02 18 94 00 01 03 00 03 00 0d 00 02")
    }

    override fun parseResponse(bodyBuf: ByteArray): Any? {
        return super.parseResponse(bodyBuf)
    }
}

class SetupCmd3(socket: Socket?): BaseParser(socket) {
    override fun setup()  {
        sendPkg = KtdxHelper.hexStringToByteArray("0c 03 18 99 00 01 20 00 20 00 db 0f d5" +
                "d0 c9 cc d6 a4 a8 af 00 00 00 8f c2 25" +
                "40 13 00 00 d5 00 c9 cc bd f0 d7 ea 00" +
                "00 00 02")
    }

    override fun parseResponse(bodyBuf: ByteArray): Any? {
        return super.parseResponse(bodyBuf)
    }
}