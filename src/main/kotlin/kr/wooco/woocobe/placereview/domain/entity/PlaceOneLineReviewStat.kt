package kr.wooco.woocobe.placereview.domain.entity

data class PlaceOneLineReviewStat(
    val id: Long,
    val contents: Contents,
    val count: Long,
) {
    @JvmInline
    value class Contents(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "한줄평 내용이 없습니다." }
        }
    }

    init {
        require(count >= 0) { "한줄평 통계의 개수는 0 이상이어야 합니다." }
    }

    fun increaseCount(): PlaceOneLineReviewStat = copy(count = count + 1)

    fun decreaseCount(): PlaceOneLineReviewStat = copy(count = count - 1)

    companion object {
        fun create(
            contents: String,
            count: Long,
        ): PlaceOneLineReviewStat =
            PlaceOneLineReviewStat(
                id = 0L,
                contents = Contents(contents),
                count = count,
            )
    }
}
