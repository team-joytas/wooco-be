package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface WithdrawUseCase {
    fun withdraw(
        userId: Long,
        refreshToken: String,
    )
}
