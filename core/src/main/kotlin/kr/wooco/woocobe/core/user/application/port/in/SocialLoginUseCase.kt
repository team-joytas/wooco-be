package kr.wooco.woocobe.core.user.application.port.`in`

fun interface SocialLoginUseCase {
    data class Command(
        val socialId: String,
        val socialType: String,
    )

    fun socialLogin(command: Command): Long
}
