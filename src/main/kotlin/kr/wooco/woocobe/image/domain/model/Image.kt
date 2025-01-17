package kr.wooco.woocobe.image.domain.model

data class Image(
    val imageUrl: String,
    val uploadUrl: String,
) {
    init {
        require(imageUrl.isNotBlank()) { "imageUrl cannot be blank" }
        require(uploadUrl.isNotBlank()) { "uploadUrl cannot be blank" }
    }
}
