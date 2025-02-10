package kr.wooco.woocobe.placereview.domain.entity

data class PlaceOneLineReviewStat(
    val id: Long,
    val contents: String,
    var count: Long,
) {
    init {
        require(count >= 0) { "한줄평 통계의 개수는 0 이상이어야 합니다." }
    }

    fun increaseCount() {
        count++
    }

    fun decreaseCount() {
        require(count > 0) { "한줄평 통계의 개수는 0 이하로 줄어들 수 없습니다." }
        count--
    }

    companion object {
        fun create(
            contents: String,
            count: Long,
        ): PlaceOneLineReviewStat =
            PlaceOneLineReviewStat(
                id = 0L,
                contents = contents,
                count = count,
            )
    }
}
