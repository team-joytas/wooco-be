package kr.wooco.woocobe.api.notification.response

import kr.wooco.woocobe.core.notification.application.port.`in`.results.NotificationResult
import java.time.LocalDateTime

data class NotificationDetailResponse(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val type: String,
    val createdAt: LocalDateTime,
    val readStatus: String,
) {
    companion object {
        fun from(notificationResult: NotificationResult): NotificationDetailResponse =
            NotificationDetailResponse(
                id = notificationResult.id,
                userId = notificationResult.userId,
                targetId = notificationResult.targetId,
                targetName = notificationResult.targetName,
                type = notificationResult.type,
                createdAt = notificationResult.createdAt,
                readStatus = notificationResult.readStatus,
            )

        fun listFrom(notificationResults: List<NotificationResult>): List<NotificationDetailResponse> = notificationResults.map { from(it) }
    }
}
