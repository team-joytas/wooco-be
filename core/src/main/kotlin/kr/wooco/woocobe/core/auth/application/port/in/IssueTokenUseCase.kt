package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface IssueTokenUseCase {
    data class Command(
        val userId: Long,
    )

    fun issueToken(command: Command): Long
}
