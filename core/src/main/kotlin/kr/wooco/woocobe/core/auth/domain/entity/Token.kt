package kr.wooco.woocobe.core.auth.domain.entity

data class Token(
    val id: Long,
    val userId: Long,
) {
    fun validateUserId(userId: Long) {
        if (this.userId != userId) throw RuntimeException()
    }

    companion object {
        fun create(userId: Long) =
            Token(
                id = 0L,
                userId = userId,
            )
    }
}
