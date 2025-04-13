package kr.wooco.woocobe.core.placereview.domain.entity

data class PlaceOneLineReview(
    val id: Long,
    val placeReviewId: Long,
    val placeId: Long,
    val contents: Contents,
) {
    @JvmInline
    value class Contents(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "한줄평 내용이 없습니다." }
            require(value.length <= 7) { "한줄평 내용은 7자를 넘을 수 없습니다." }
        }
    }

    companion object {
        private fun isDuplicateContent(contentsList: List<String>) {
            require(contentsList.distinct().size == contentsList.size) { "중복된 한줄평이 존재합니다." }
        }

        fun create(
            placeId: Long,
            placeReviewId: Long,
            contentsList: List<String>,
        ): List<PlaceOneLineReview> {
            isDuplicateContent(contentsList)
            return contentsList.map {
                PlaceOneLineReview(
                    id = 0L,
                    placeId = placeId,
                    placeReviewId = placeReviewId,
                    contents = Contents(it),
                )
            }
        }
    }
}
