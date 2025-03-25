package kr.wooco.woocobe.core.user.application.port.out

import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.vo.SocialUser

interface LoadUserPersistencePort {
    fun getByUserId(userId: Long): User

    fun getOrNullBySocialUser(socialUser: SocialUser): User?

    fun getAllByUserIds(userIds: List<Long>): List<User>
}
