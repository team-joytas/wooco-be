package kr.wooco.woocobe.fcm.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.core.notification.application.port.out.NotificationSenderPort
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.Token
import org.springframework.stereotype.Component

@Component
internal class NotificationExternalFcmAdapter(
    private val firebaseMessaging: FirebaseMessaging,
) : NotificationSenderPort {
    override fun sendNotification(
        notification: Notification,
        tokens: List<Token>,
    ) {
        val messages = MulticastMessage
            .builder()
            .addAllTokens(tokens.map { it.value })
            .putData("notification_id", notification.id.toString())
            .putData("user_id", notification.userId.toString())
            .putData("target_id", notification.targetId.toString())
            .putData("target_name", notification.targetName)
            .putData("type", notification.type.name)
            .build()
        return sendWithLogging(messages)
    }

    private fun sendWithLogging(message: MulticastMessage) {
        val response = firebaseMessaging.sendEachForMulticast(message)
        if (response.failureCount > 0) {
            log.warn { "FCM push failed count: ${response.failureCount}" }
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
