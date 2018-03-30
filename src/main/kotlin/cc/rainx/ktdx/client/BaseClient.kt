package cc.rainx.ktdx.client

import cc.rainx.ktdx.parser.*
import cc.rainx.ktdx.parser.params.GetSecurityListParams
import cc.rainx.ktdx.parser.params.RawPkgParams
import cc.rainx.ktdx.parser.result.SecurityListEntry
import java.net.Socket

open class BaseClient {

    var socket:Socket? = null

    fun setup() {
        SetupCmd1(socket).callApi()
        SetupCmd2(socket).callApi()
        SetupCmd3(socket).callApi()
    }

    fun connect(ip:String="101.227.73.20", port:Int=7709) {
        socket = Socket(ip, port)
        setup()
    }

    fun disconnect() {
        socket?.close()
    }

    fun close() = disconnect()

    fun getSecurityList(market:Int, start:Int):MutableList<SecurityListEntry>? {
        val cmd = GetSecurityList(socket)
        cmd.setParams(GetSecurityListParams(market, start))
        return cmd.callApi() as MutableList<SecurityListEntry>?
    }

    fun sendRawPkg(rawPkg: ByteArray): Any? {
        val cmd = SendRawPkg(socket)
        cmd.setParams(RawPkgParams(rawPkg))
        return cmd.callApi()
    }
}