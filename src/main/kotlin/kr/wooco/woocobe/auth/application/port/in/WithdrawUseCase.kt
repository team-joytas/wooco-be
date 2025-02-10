package kr.wooco.woocobe.auth.application.port.`in`

fun interface WithdrawUseCase {
    fun withdraw(
        userId: Long,
        refreshToken: String,
    )
}
