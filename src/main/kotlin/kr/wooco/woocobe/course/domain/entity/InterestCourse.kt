package kr.wooco.woocobe.course.domain.entity

class InterestCourse(
    val id: Long,
    val userId: Long,
    val courseId: Long,
) {
    companion object {
        fun create(
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
