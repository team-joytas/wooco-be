package kr.wooco.woocobe.core.user.application.port.`in`

fun interface WithdrawUserUseCase {
    data class Command(
        val userId: Long,
        val socialToken: String,
    )

    fun withdrawUser(command: Command)
}
