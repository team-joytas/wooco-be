package kr.wooco.woocobe.core.user.domain.entity

import kr.wooco.woocobe.core.user.domain.exception.InActiveUserException
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import kr.wooco.woocobe.core.user.domain.vo.UserProfile
import kr.wooco.woocobe.core.user.domain.vo.UserStatus

data class User(
    val id: Long,
    val profile: UserProfile,
    val status: UserStatus,
    val socialUser: SocialUser,
) {
    init {
        if (status == UserStatus.ACTIVE) {
            require(profile.name.isNotBlank()) { "이름은 공백이 될 수 없습니다." }
        }
    }

    fun login(): User =
        copy(
            status = if (status == UserStatus.INACTIVE) {
                UserStatus.ONBOARDING
            } else {
                status
            },
        )

    fun withdraw(): User {
        when {
            status == UserStatus.INACTIVE -> throw InActiveUserException
        }
        return copy(status = UserStatus.INACTIVE)
    }

    fun updateProfile(profile: UserProfile): User {
        when {
            status == UserStatus.INACTIVE -> throw InActiveUserException
        }

        return copy(
            status = UserStatus.ACTIVE,
            profile = profile,
        )
    }

    companion object {
        fun create(socialUser: SocialUser): User =
            User(
                id = 0L,
                profile = UserProfile(
                    name = "",
                    profileUrl = "",
                    description = "",
                ),
                status = UserStatus.ONBOARDING,
                socialUser = socialUser,
            )
    }
}
