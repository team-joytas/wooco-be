package kr.wooco.woocobe.image.domain.gateway

import kr.wooco.woocobe.image.domain.model.ImageKey

interface ImageClientGateway {
    fun fetchImageUrl(imageKey: ImageKey): String

    fun fetchImageUploadUrl(imageKey: ImageKey): String
}
