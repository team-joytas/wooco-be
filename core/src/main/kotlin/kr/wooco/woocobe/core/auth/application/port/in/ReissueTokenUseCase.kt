package kr.wooco.woocobe.core.auth.application.port.`in`

fun interface ReissueTokenUseCase {
    data class Command(
        val tokenId: Long,
        val userId: Long,
    )

    fun reissueToken(command: Command): Long
}
