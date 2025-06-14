package kr.wooco.woocobe.api.user.response

import kr.wooco.woocobe.core.user.application.port.`in`.results.UserDetailsResult

data class UserDetailsResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val status: String,
    val reviewCount: Long,
    val courseCount: Long,
    val likeCourseCount: Long,
) {
    companion object {
        fun from(userDetailsResult: UserDetailsResult): UserDetailsResponse =
            UserDetailsResponse(
                userId = userDetailsResult.id,
                name = userDetailsResult.name,
                profileUrl = userDetailsResult.profileUrl,
                description = userDetailsResult.description,
                status = userDetailsResult.status,
                reviewCount = userDetailsResult.reviewCount,
                courseCount = userDetailsResult.courseCount,
                likeCourseCount = userDetailsResult.interestCourseCount,
            )
    }
}
