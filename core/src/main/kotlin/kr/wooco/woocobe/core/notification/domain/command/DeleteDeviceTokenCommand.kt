package kr.wooco.woocobe.core.notification.domain.command

data class DeleteDeviceTokenCommand(
    val userId: Long,
    val tokenId: Long,
)
