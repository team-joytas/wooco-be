package kr.wooco.woocobe.user.domain.gateway

import kr.wooco.woocobe.user.domain.model.User

interface UserStorageGateway {
    fun save(user: User): User
}