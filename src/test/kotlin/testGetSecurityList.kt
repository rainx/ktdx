import cc.rainx.ktdx.client.BaseClient
import cc.rainx.ktdx.helper.KtdxHelper

fun main(args:Array<String>) {
    val client = BaseClient()
    client.connect()
    client.sendRawPkg(KtdxHelper.hexStringToByteArray("0c0218930001030003000d0001"))

    val result = client.getSecurityList(0, 0)
    println("result len is ${result?.count()}")

    client.disconnect()
}