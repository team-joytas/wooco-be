package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface LogoutUseCase {
    fun logout(refreshToken: String)
}
