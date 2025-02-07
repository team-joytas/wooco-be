package kr.wooco.woocobe.image.adapter.`in`.web.response

import kr.wooco.woocobe.image.application.port.`in`.results.UploadImageUrlResult

data class ImageUploadUrlResponse(
    val imageUrl: String,
    val uploadUrl: String,
) {
    companion object {
        private const val IMAGE_CDN_URL = "https://cdn.wooco.kr/" // TODO: 리팩토링

        fun from(uploadImageUrlResult: UploadImageUrlResult): ImageUploadUrlResponse =
            ImageUploadUrlResponse(
                imageUrl = IMAGE_CDN_URL + uploadImageUrlResult.imageKey,
                uploadUrl = uploadImageUrlResult.uploadUrl,
            )
    }
}
