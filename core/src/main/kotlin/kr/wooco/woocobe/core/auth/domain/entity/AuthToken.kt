package kr.wooco.woocobe.core.auth.domain.entity

import java.util.UUID

data class AuthToken(
    val id: String,
    val userId: Long,
) {
    fun rotateAuthToken(): AuthToken =
        copy(
            id = generateAuthTokenId(),
        )

    companion object {
        fun create(userId: Long) =
            AuthToken(
                id = generateAuthTokenId(),
                userId = userId,
            )

        private fun generateAuthTokenId(): String = UUID.randomUUID().toString()
    }
}
