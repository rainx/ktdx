package cc.rainx.ktdx.parser.params

open class Params

data class GetSecurityListParams(val market:Int, val start:Int): Params()

data class RawPkgParams(val rawPkg: ByteArray): Params()

