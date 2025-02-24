package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.SendNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.out.SendNotificationPort
import org.springframework.stereotype.Service

@Service
class SendNotificationService(
    private val sendNotificationPort: SendNotificationPort,
) : SendNotificationUseCase {
    override fun sendNotification(request: SendNotificationUseCase.Request) {
        sendNotificationPort.sendNotification(
            notification = request.notification,
            token = request.token,
        )
    }
}
