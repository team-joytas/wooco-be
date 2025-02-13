package kr.wooco.woocobe.core.user.application.port.`in`

import kr.wooco.woocobe.core.user.application.port.`in`.results.UserResult

fun interface ReadUserUseCase {
    data class Query(
        val userId: Long,
    )

    fun readUser(query: Query): UserResult
}
