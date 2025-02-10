package kr.wooco.woocobe.auth.application.port.`in`

fun interface ExtractTokenUseCase {
    fun extractToken(accessToken: String): Long
}
