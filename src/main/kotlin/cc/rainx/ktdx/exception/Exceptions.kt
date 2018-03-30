package cc.rainx.ktdx.exception

class SocketClientNotReady(message:String): Exception(message)

class SendPkgNotReady(message: String): Exception(message)

class SendRequestPkgFails(message: String): Exception(message)

class ResponseHeaderRecvFails(message: String): Exception(message)

class ResponseRecvFails(message: String): Exception(message)


