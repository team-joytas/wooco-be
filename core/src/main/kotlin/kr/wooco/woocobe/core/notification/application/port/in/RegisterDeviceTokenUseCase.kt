package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface RegisterDeviceTokenUseCase {
    data class Command(
        val userId: Long,
        val token: String,
    )

    /**
     * FCM 토큰을 등록합니다.
     *
     * 만약 동일한 토큰이 이미 존재한다면, 예외가 발생하지 않고 FCM 토큰을 추가로 등록하지 않습니다.
     *
     * @author Junseoparkk
     */
    fun registerDeviceToken(command: Command)
}
