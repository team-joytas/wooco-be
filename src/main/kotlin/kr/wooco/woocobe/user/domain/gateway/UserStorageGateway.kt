package kr.wooco.woocobe.user.domain.gateway

import kr.wooco.woocobe.user.domain.model.User

interface UserStorageGateway {
    fun save(user: User): User

    fun update(user: User): User

    fun getByUserId(userId: Long): User?

    fun deleteByUserId(userId: Long)
}
