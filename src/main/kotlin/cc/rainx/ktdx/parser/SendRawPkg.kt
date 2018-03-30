package cc.rainx.ktdx.parser

import cc.rainx.ktdx.helper.KtdxHelper
import cc.rainx.ktdx.parser.params.RawPkgParams
import java.net.Socket

class SendRawPkg(socket: Socket?): BaseParser(socket) {

    fun setParams(params: RawPkgParams) {
        logger.info("raw pkg is ${KtdxHelper.byteArrayToHexString(params.rawPkg, true)}, len is ${params.rawPkg.count()}")
        sendPkg = params.rawPkg
    }

    override fun parseResponse(bodyBuf: ByteArray): Any? {
        logger.info("rsp pkg is :\n${KtdxHelper.byteArrayToHexString(bodyBuf, true)}")
        logger.info("rsp pkg len is : ${bodyBuf.count()}")
        return null
    }
}