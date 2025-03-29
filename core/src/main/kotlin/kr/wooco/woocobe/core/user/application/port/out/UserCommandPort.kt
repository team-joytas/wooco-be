package kr.wooco.woocobe.core.user.application.port.out

import kr.wooco.woocobe.core.user.domain.entity.User

interface UserCommandPort {
    fun saveUser(user: User): User

    fun deleteByUserId(userId: Long)
}
