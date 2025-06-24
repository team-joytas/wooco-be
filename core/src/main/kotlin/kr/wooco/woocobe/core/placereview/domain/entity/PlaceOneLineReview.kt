package kr.wooco.woocobe.core.placereview.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot

data class PlaceOneLineReview(
    override val id: Long,
    val placeReviewId: Long,
    val placeId: Long,
    val content: Content,
) : AggregateRoot() {
    @JvmInline
    value class Content(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "한줄평 내용이 없습니다." }
            require(value.length <= 7) { "한줄평 내용은 7자를 넘을 수 없습니다." }
        }
    }

    companion object {
        private fun isDuplicateContent(contents: List<String>) {
            require(contents.distinct().size == contents.size) { "중복된 한줄평이 존재합니다." }
        }

        // TODO: 한줄평 애거리거트 식별자 생성 위임 예정
        fun create(
            placeId: Long,
            placeReviewId: Long,
            contents: List<String>,
        ): List<PlaceOneLineReview> {
            isDuplicateContent(contents)
            return contents.map { content ->
                PlaceOneLineReview(
                    id = 0L,
                    placeId = placeId,
                    placeReviewId = placeReviewId,
                    content = Content(content),
                )
            }
        }
    }
}
