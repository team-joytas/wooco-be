package kr.wooco.woocobe.core.user.application.port.`in`

import kr.wooco.woocobe.core.user.application.port.`in`.results.UserInfoResult

fun interface ReadUserInfoUseCase {
    data class Query(
        val userId: Long,
    )

    fun readUserInfo(query: Query): UserInfoResult
}
