package kr.wooco.woocobe.api.notification.response

import kr.wooco.woocobe.core.notification.application.port.`in`.results.NotificationResult
import java.time.LocalDateTime

data class NotificationDetailResponse(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    var isRead: Boolean,
    val type: String,
    val sentAt: LocalDateTime,
) {
    companion object {
        fun from(notificationResult: NotificationResult): NotificationDetailResponse =
            NotificationDetailResponse(
                id = notificationResult.id,
                userId = notificationResult.userId,
                targetId = notificationResult.targetId,
                targetName = notificationResult.targetName,
                isRead = notificationResult.isRead,
                type = notificationResult.type,
                sentAt = notificationResult.sentAt,
            )

        fun listFrom(notificationResults: List<NotificationResult>): List<NotificationDetailResponse> = notificationResults.map { from(it) }
    }
}
