package kr.wooco.woocobe.auth.application.port.`in`

fun interface LogoutUseCase {
    fun logout(refreshToken: String)
}
