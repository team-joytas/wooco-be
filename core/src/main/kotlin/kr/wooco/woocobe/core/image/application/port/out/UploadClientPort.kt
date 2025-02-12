package kr.wooco.woocobe.core.image.application.port.out

interface UploadClientPort {
    fun fetchUploadUrl(key: String): String
}
