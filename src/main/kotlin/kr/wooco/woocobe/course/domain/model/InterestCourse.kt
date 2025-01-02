package kr.wooco.woocobe.course.domain.model

class InterestCourse(
    val id: Long,
    val userId: Long,
    val courseId: Long,
) {
    companion object {
        fun register(
            userId: Long,
            courseId: Long,
        ): InterestCourse =
            InterestCourse(
                id = 0L,
                userId = userId,
                courseId = courseId,
            )
    }
}
