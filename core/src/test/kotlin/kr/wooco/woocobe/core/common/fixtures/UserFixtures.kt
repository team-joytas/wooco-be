package kr.wooco.woocobe.core.common.fixtures

import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.vo.SocialType
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import kr.wooco.woocobe.core.user.domain.vo.UserProfile
import kr.wooco.woocobe.core.user.domain.vo.UserStatus

object UserFixtures {
    const val DEFAULT_USER_ID: Long = 100L

    fun activeUser(
        id: Long = DEFAULT_USER_ID,
        name: String = "우코유저",
        status: UserStatus = UserStatus.ACTIVE,
    ): User =
        User(
            id = id,
            profile =
                UserProfile(
                    name = name,
                    profileUrl = "https://image.wooco.com/profile/.png",
                    description = "",
                ),
            status = status,
            socialUser =
                SocialUser(
                    socialId = "social-11",
                    socialType = SocialType.KAKAO,
                ),
        )
}
