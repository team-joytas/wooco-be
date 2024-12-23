package kr.wooco.woocobe.auth.domain.model

class AuthToken(
    val id: Long,
    val userId: Long,
) {
    fun isMatchUserId(targetId: Long): Boolean = userId == targetId

    companion object {
        fun register(userId: Long): AuthToken =
            AuthToken(
                id = 0L,
                userId = userId,
            )
    }
}
