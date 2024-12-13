package kr.wooco.woocobe.auth.domain.model

import kr.wooco.woocobe.common.domain.IdGenerator

class AuthToken(
    val userId: Long,
    var tokenId: Long,
) {
    fun regenerateId(): AuthToken =
        apply {
            tokenId = IdGenerator.generateId()
        }

    fun isMatchUserId(targetUserId: Long): Boolean = userId == targetUserId

    companion object {
        fun register(userId: Long): AuthToken =
            AuthToken(
                tokenId = IdGenerator.generateId(),
                userId = userId,
            )
    }
}
