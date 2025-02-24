package kr.wooco.woocobe.fcm.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kr.wooco.woocobe.core.notification.application.port.out.SendNotificationPort
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import org.springframework.stereotype.Component

@Component
internal class SendNotificationFcmAdapter(
    private val firebaseMessaging: FirebaseMessaging,
) : SendNotificationPort {
    override fun sendNotification(
        notification: Notification,
        token: String,
    ) {
        val message = Message
            .builder()
            .setToken(token)
            .putData("notificationId", notification.id.toString())
            .putData("userId", notification.userId.toString())
            .putData("targetId", notification.targetId.toString())
            .putData("targetName", notification.targetName)
            .putData("type", notification.type.name)
            .putData("isRead", notification.isRead.toString())
            .build()
        firebaseMessaging.send(message)
    }
}
