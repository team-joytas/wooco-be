package kr.wooco.woocobe.image.ui.web.controller.response

import kr.wooco.woocobe.image.domain.model.Image

data class ImageUploadUrlResponse(
    val imageUrl: String,
    val uploadUrl: String,
) {
    companion object {
        fun from(image: Image): ImageUploadUrlResponse =
            ImageUploadUrlResponse(
                imageUrl = image.imageUrl,
                uploadUrl = image.uploadUrl,
            )
    }
}
