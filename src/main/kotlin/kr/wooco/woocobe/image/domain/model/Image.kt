package kr.wooco.woocobe.image.domain.model

data class Image(
    val imagePath: String,
    val uploadUrl: String,
) {
    constructor(imageKey: ImageKey, uploadUrl: String) : this(
        imagePath = imageKey.key,
        uploadUrl = uploadUrl,
    )

    init {
        require(imagePath.isNotBlank()) { "imageUrl cannot be blank" }
        require(uploadUrl.isNotBlank()) { "uploadUrl cannot be blank" }
    }
}
