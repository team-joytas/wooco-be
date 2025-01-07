package kr.wooco.woocobe.image.domain.gateway

import kr.wooco.woocobe.image.domain.model.ImageKey

interface ImageClientGateway {
    fun fetchImageUploadUrl(imageKey: ImageKey): String
}
