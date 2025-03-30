package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface DeleteTokenUseCase {
    data class Command(
        val userId: Long,
        val tokenId: Long,
    )

    fun deleteToken(command: Command)
}
