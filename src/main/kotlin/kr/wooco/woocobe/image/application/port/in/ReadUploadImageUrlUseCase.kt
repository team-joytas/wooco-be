package kr.wooco.woocobe.image.application.port.`in`

import kr.wooco.woocobe.image.application.port.`in`.results.UploadImageUrlResult

fun interface ReadUploadImageUrlUseCase {
    fun readUploadImageUrl(): UploadImageUrlResult
}
