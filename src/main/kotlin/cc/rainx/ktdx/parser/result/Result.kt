package cc.rainx.ktdx.parser.result

open class Result<T> {
    var success: Boolean = false
    var data: T? = null
}


data class SecurityListEntry(val code:String, val volUnit:Int, val decimalPoint:Int, val name:String, val preClose: Float)
