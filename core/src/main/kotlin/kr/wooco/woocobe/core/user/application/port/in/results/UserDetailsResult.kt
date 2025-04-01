package kr.wooco.woocobe.core.user.application.port.`in`.results

import kr.wooco.woocobe.core.user.domain.entity.User

data class UserDetailsResult(
    val id: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val status: String,
    val reviewCount: Long,
    val courseCount: Long,
    val interestCourseCount: Long,
) {
    companion object {
        fun of(
            user: User,
            reviewCount: Long,
            courseCount: Long,
            interestCoursesCount: Long,
        ): UserDetailsResult =
            UserDetailsResult(
                id = user.id,
                name = user.profile.name,
                profileUrl = user.profile.profileUrl,
                description = user.profile.description,
                status = user.status.name,
                reviewCount = reviewCount,
                courseCount = courseCount,
                interestCourseCount = interestCoursesCount,
            )
    }
}
