package kr.wooco.woocobe.placereview.domain.entity

data class PlaceOneLineReviewStat(
    val id: Long,
    val contents: String,
    var count: Long,
) {
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

    fun increaseCount() {
        count++
    }

    fun decreaseCount() {
        if (count > 0) count--
    }
}
