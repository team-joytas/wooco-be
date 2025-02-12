package kr.wooco.woocobe.core.image.application.port.`in`

import kr.wooco.woocobe.core.image.application.port.`in`.results.UploadImageUrlResult

fun interface ReadUploadImageUrlUseCase {
    fun readUploadImageUrl(): UploadImageUrlResult
}
