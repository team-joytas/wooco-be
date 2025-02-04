package kr.wooco.woocobe.user.application.port.out

import kr.wooco.woocobe.user.domain.entity.User

interface LoadUserPersistencePort {
    fun getByUserId(userId: Long): User

    fun getAllByUserIds(userIds: List<Long>): List<User>
}
