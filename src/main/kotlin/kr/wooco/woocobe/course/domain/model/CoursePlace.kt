package kr.wooco.woocobe.course.domain.model

data class CoursePlace(
    val order: Int,
    val placeId: Long,
) {
    init {
        require(order in 0..4) { "order must be between 0 and 4" }
    }
}
