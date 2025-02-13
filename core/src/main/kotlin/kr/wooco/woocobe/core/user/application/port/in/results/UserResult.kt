package kr.wooco.woocobe.core.user.application.port.`in`.results

import kr.wooco.woocobe.core.user.domain.entity.User

data class UserResult(
    val id: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val status: String,
) {
    companion object {
        fun fromUser(user: User): UserResult =
            UserResult(
                id = user.id,
                name = user.profile.name,
                profileUrl = user.profile.profileUrl,
                description = user.profile.description,
                status = user.status.name,
            )
    }
}
