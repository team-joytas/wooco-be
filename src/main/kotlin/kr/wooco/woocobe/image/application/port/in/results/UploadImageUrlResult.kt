package kr.wooco.woocobe.image.application.port.`in`.results

data class UploadImageUrlResult(
    val imageKey: String,
    val uploadUrl: String,
) {
    companion object {
        fun of(
            imageKey: String,
            uploadUrl: String,
        ): UploadImageUrlResult =
            UploadImageUrlResult(
                imageKey = imageKey,
                uploadUrl = uploadUrl,
            )
    }
}
