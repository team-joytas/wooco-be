package kr.wooco.woocobe.user.application.port.`in`

fun interface WithdrawUserUseCase {
    data class Command(
        val userId: Long,
    )

    fun withdrawUser(command: Command)
}
