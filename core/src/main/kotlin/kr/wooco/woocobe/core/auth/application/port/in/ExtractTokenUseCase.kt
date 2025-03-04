package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface ExtractTokenUseCase {
    fun extractToken(accessToken: String): Long
}
