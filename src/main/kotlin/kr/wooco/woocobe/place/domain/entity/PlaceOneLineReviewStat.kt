package kr.wooco.woocobe.place.domain.entity

data class PlaceOneLineReviewStat(
    val contents: String,
    var count: Long,
) {
    fun of(
        contents: String,
        count: Long,
    ): PlaceOneLineReviewStat = PlaceOneLineReviewStat(contents, count)

    fun increaseCount() {
        count++
    }

    fun decreaseCount() {
        if (count > 0) count--
    }
}
