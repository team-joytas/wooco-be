package kr.wooco.woocobe.image.application.port.out

interface UploadClientPort {
    fun fetchUploadUrl(key: String): String
}
