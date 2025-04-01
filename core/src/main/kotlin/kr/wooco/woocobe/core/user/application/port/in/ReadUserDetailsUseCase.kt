package kr.wooco.woocobe.core.user.application.port.`in`

import kr.wooco.woocobe.core.user.application.port.`in`.results.UserDetailsResult

fun interface ReadUserDetailsUseCase {
    data class Query(
        val userId: Long,
    )

    fun readUserDetails(query: Query): UserDetailsResult
}
