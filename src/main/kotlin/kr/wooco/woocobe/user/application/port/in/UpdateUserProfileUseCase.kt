package kr.wooco.woocobe.user.application.port.`in`

fun interface UpdateUserProfileUseCase {
    data class Command(
        val userId: Long,
        val name: String,
        val profileUrl: String,
        val description: String,
    )

    fun updateUserProfile(command: Command)
}
