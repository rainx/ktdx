package cc.rainx.ktdx.parser

import cc.rainx.ktdx.exception.ResponseRecvFails
import cc.rainx.ktdx.helper.KtdxHelper
import java.net.Socket

import cc.rainx.ktdx.parser.params.Params
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.logging.Logger


val RSP_HEADER_LEN:Int = 0x10

abstract class BaseParser(val socket: Socket?) {

    val logger = Logger.getLogger(BaseParser::class.java.canonicalName)
    var sendPkg: ByteArray? = null

    open fun setParams(params: Params) {

    }

    open fun parseResponse(bodyBuf: ByteArray): Any? {
        return null
    }

    open fun setup() {

    }

    fun callApi(): Any? {
        setup()

        if (socket == null) {
            return null
        }

        val inputStream = socket.getInputStream().buffered(1024)
        val outputStream = socket.getOutputStream()
        // send package
        outputStream.write(sendPkg)
        outputStream.flush()
        if (sendPkg != null) {
            logger.info("sending pkg is ${KtdxHelper.byteArrayToHexString(sendPkg!!)}")
        }
        val headerBytes = ByteArray(RSP_HEADER_LEN)
        inputStream.read(headerBytes)
        logger.info("header is : ${KtdxHelper.byteArrayToHexString(headerBytes)}")
        if (headerBytes.count() == RSP_HEADER_LEN) {
            val headerBuf = ByteBuffer.wrap(headerBytes)
            headerBuf.order(ByteOrder.LITTLE_ENDIAN)
            headerBuf.position(12)
            val zipSize = headerBuf.getShort().toInt() and 0xffff
            logger.info("zipsize is $zipSize")
            val unzipSize = headerBuf.getShort().toInt() and 0xffff
            var baos = ByteArrayOutputStream(zipSize)
            var basize = 0
            var off = 0
            while (true) {
                val bytes = ByteArray(zipSize)
                val pktsize = inputStream.read(bytes, 0, zipSize)
                basize += pktsize
                baos.write(bytes, 0, pktsize)
                logger.info("pkg off: ${off} is : ${KtdxHelper.byteArrayToHexString(bytes)}")
                off += pktsize
                if (pktsize == 0 || basize == zipSize) {
                    break
                }
            }

            if (basize == 0) {
                throw ResponseRecvFails("接收数据体失败服务器断开连接")
            }

            var bodyBuf:ByteArray = ByteArray(unzipSize.toInt())
            if (zipSize == unzipSize) {
                // 不需要解压
                bodyBuf = baos.toByteArray()
            } else {
                val unzipedBodyBuf = baos.toByteArray()
                bodyBuf = KtdxHelper.decompress(unzipedBodyBuf)
            }

            return parseResponse(bodyBuf)

        } else {
            logger.info("Header Length is not ${RSP_HEADER_LEN}")
            throw Exception("head_buf is not 0x10 : ${headerBytes.count()}")
        }

        return null
    }


}