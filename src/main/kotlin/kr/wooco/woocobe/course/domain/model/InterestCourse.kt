package kr.wooco.woocobe.course.domain.model

class InterestCourse(
    val id: Long,
    val userId: Long,
    val course: Course,
) {
    companion object {
        fun register(
            userId: Long,
            course: Course,
        ): InterestCourse =
            InterestCourse(
                id = 0L,
                userId = userId,
                course = course,
            )
    }
}
