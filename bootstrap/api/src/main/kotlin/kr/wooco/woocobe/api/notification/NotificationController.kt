package kr.wooco.woocobe.api.notification

import kr.wooco.woocobe.api.notification.request.CreateDeviceTokenRequest
import kr.wooco.woocobe.api.notification.response.NotificationDetailResponse
import kr.wooco.woocobe.core.notification.application.port.`in`.CreateDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.DeleteDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadAllNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.UpdateNotificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val readAllNotificationUseCase: ReadAllNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val createDeviceTokenUseCase: CreateDeviceTokenUseCase,
    private val deleteDeviceTokenUseCase: DeleteDeviceTokenUseCase,
) : NotificationApi {
    @GetMapping
    override fun getAllUserNotification(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<NotificationDetailResponse>> {
        val query = ReadAllNotificationUseCase.Query(userId)
        val results = readAllNotificationUseCase.readAllNotification(query)
        return ResponseEntity.ok(NotificationDetailResponse.listFrom(results))
    }

    @PatchMapping("/{notificationId}")
    override fun updateNotification(
        @AuthenticationPrincipal userId: Long,
        @PathVariable notificationId: Long,
    ) {
        val command = UpdateNotificationUseCase.Command(notificationId)
        updateNotificationUseCase.updateNotification(command)
    }

    @PostMapping
    override fun createDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateDeviceTokenRequest,
    ) {
        val command = CreateDeviceTokenUseCase.Command(
            userId = userId,
            token = request.token,
        )
        createDeviceTokenUseCase.createDeviceToken(command)
    }

    @DeleteMapping("/{token}")
    override fun deleteDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @PathVariable token: String,
    ) {
        val command = DeleteDeviceTokenUseCase.Command(token)
        deleteDeviceTokenUseCase.deleteDeviceToken(command)
    }
}
