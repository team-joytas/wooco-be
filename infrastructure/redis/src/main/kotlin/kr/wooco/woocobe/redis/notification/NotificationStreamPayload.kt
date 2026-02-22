package kr.wooco.woocobe.redis.notification

import kr.wooco.woocobe.core.notification.domain.event.NotificationCreatedEvent
import org.springframework.data.redis.connection.stream.MapRecord

data class NotificationStreamPayload(
    val notificationId: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val type: String,
) {
    fun toFieldMap(): Map<String, String> =
        mapOf(
            FIELD_NOTIFICATION_ID to notificationId.toString(),
            FIELD_USER_ID to userId.toString(),
            FIELD_TARGET_ID to targetId.toString(),
            FIELD_TARGET_NAME to targetName,
            FIELD_TYPE to type,
        )

    companion object {
        private const val FIELD_NOTIFICATION_ID = "notificationId"
        private const val FIELD_USER_ID = "userId"
        private const val FIELD_TARGET_ID = "targetId"
        private const val FIELD_TARGET_NAME = "targetName"
        private const val FIELD_TYPE = "type"

        fun from(event: NotificationCreatedEvent): NotificationStreamPayload =
            NotificationStreamPayload(
                notificationId = event.aggregateId,
                userId = event.userId,
                targetId = event.targetId,
                targetName = event.targetName,
                type = event.type,
            )

        fun from(record: MapRecord<String, String, String>): NotificationStreamPayload? {
            val fields = record.value
            return NotificationStreamPayload(
                notificationId = fields[FIELD_NOTIFICATION_ID]?.toLongOrNull() ?: return null,
                userId = fields[FIELD_USER_ID]?.toLongOrNull() ?: 0L,
                targetId = fields[FIELD_TARGET_ID]?.toLongOrNull() ?: 0L,
                targetName = fields[FIELD_TARGET_NAME] ?: "",
                type = fields[FIELD_TYPE] ?: "",
            )
        }
    }
}
