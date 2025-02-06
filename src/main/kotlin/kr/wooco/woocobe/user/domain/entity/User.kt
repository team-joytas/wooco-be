package kr.wooco.woocobe.user.domain.entity

import kr.wooco.woocobe.user.domain.vo.UserProfile

class User(
    val id: Long,
    var profile: UserProfile,
) {
    fun updateProfile(profile: UserProfile) {
        this.profile = profile
    }

    companion object {
        private const val DEFAULT_NAME = "우코"
        private const val DEFAULT_PROFILE_URL = "https://cdn.wooco.kr/wooco-default-logo"
        private const val DEFAULT_DESCRIPTION = "아직 소개글을 작성하지 않았습니다."

        fun createDefault(): User =
            User(
                id = 0L,
                profile = UserProfile(
                    name = DEFAULT_NAME,
                    profileUrl = DEFAULT_PROFILE_URL,
                    description = DEFAULT_DESCRIPTION,
                ),
            )
    }
}
