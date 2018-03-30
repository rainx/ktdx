import cc.rainx.ktdx.helper.KtdxHelper
import java.nio.ByteBuffer
import java.nio.charset.Charset


fun main(args:Array<String>) {
    val str = "abcd"

    val ba = str.toByteArray()
    val ba2 = str.toByteArray()


    var buf = ByteBuffer.wrap(ba)
    val s1 = buf.getShort()
    val s2 = buf.getShort()
    println("hello ${s1}, ${s2}")

    val ba3 = ba + ba2

    println(ba3.count())
    println(ba3.toString())

    println(" dafds dsfasdf dfdsfasd".replace(" ", ""))

    val batrans = KtdxHelper.hexStringToByteArray("aa bb cc")
    for (b in batrans) {
        println(b)
    }

}
