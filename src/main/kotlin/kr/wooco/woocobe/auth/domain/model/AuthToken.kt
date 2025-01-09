package kr.wooco.woocobe.auth.domain.model

import java.util.UUID

data class AuthToken(
    val userId: Long,
    val tokenId: String,
) {
    constructor(userId: Long) : this(
        userId = userId,
        tokenId = generateTokenId(),
    )

    fun rotateAuthToken(): AuthToken =
        copy(
            tokenId = generateTokenId(),
        )

    companion object {
        private fun generateTokenId(): String = UUID.randomUUID().toString()
    }
}
