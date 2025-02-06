package kr.wooco.woocobe.user.domain.entity

import kr.wooco.woocobe.user.domain.vo.UserProfile
import kr.wooco.woocobe.user.domain.vo.UserStatus

data class User(
    val id: Long,
    val profile: UserProfile,
    val status: UserStatus,
) {
    init {
        if (status != UserStatus.ONBOARDING) {
            require(profile.name.isNotBlank()) { "이름은 공백이 될 수 없습니다." }
        }
    }

    fun updateProfile(profile: UserProfile): User =
        if (status == UserStatus.ONBOARDING) {
            copy(status = UserStatus.ACTIVE, profile = profile)
        } else {
            copy(profile = profile)
        }

    companion object {
        fun createDefault(): User =
            User(
                id = 0L,
                profile = UserProfile(
                    name = "",
                    profileUrl = "",
                    description = "",
                ),
                status = UserStatus.ONBOARDING,
            )
    }
}
