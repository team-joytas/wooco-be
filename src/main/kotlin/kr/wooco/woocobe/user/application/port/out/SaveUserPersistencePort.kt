package kr.wooco.woocobe.user.application.port.out

import kr.wooco.woocobe.user.domain.entity.User

interface SaveUserPersistencePort {
    fun saveUser(user: User): User
}
