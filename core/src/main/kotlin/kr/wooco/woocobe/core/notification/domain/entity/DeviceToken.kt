package kr.wooco.woocobe.core.notification.domain.entity

data class DeviceToken(
    val id: Long,
    val userId: Long,
    val token: String,
    var isActive: Boolean,
) {
    fun disable() = apply { if (isActive) isActive = false }

    companion object {
        fun create(
            userId: Long,
            token: String,
        ): DeviceToken =
            DeviceToken(
                id = 0L,
                userId = userId,
                isActive = true,
                token = token,
            )
    }
}
