package kr.wooco.woocobe.core.user.application.port.out

import kr.wooco.woocobe.core.user.domain.entity.User

interface LoadUserPersistencePort {
    fun getByUserId(userId: Long): User

    fun getAllByUserIds(userIds: List<Long>): List<User>
}
