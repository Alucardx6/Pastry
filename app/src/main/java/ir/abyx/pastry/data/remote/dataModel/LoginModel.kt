package ir.abyx.pastry.data.remote.dataModel

data class RequestSendPhone(
    val success: Int,
    val message: String,
    val seconds: Int,
    val expireAt: String
)

data class RequestVerifyCode(
    val message: String,
    val api: String
)
