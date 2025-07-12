package kr.wooco.woocobe.api.notification

import kr.wooco.woocobe.api.notification.request.ReadDeviceTokenRequest
import kr.wooco.woocobe.api.notification.request.RegisterDeviceTokenRequest
import kr.wooco.woocobe.api.notification.request.UpdateDeviceTokenRequest
import kr.wooco.woocobe.api.notification.response.DeviceTokenDetailResponse
import kr.wooco.woocobe.api.notification.response.NotificationDetailResponse
import kr.wooco.woocobe.core.notification.application.port.`in`.DeleteDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.MarkAsReadNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadAllNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.RegisterDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.UpdateDeviceTokenUseCase
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

// TODO: 디바이스 토큰 등록 API 엔드포인트 변경

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val readAllNotificationUseCase: ReadAllNotificationUseCase,
    private val markAsReadNotificationUseCase: MarkAsReadNotificationUseCase,
    private val registerDeviceTokenUseCase: RegisterDeviceTokenUseCase,
    private val deleteDeviceTokenUseCase: DeleteDeviceTokenUseCase,
    private val updateDeviceTokenUseCase: UpdateDeviceTokenUseCase,
    private val readDeviceTokenUseCase: ReadDeviceTokenUseCase,
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
    override fun markAsReadNotification(
        @AuthenticationPrincipal userId: Long,
        @PathVariable notificationId: Long,
    ) {
        val command = MarkAsReadNotificationUseCase.Command(
            userId = userId,
            notificationId = notificationId,
        )
        markAsReadNotificationUseCase.markAsReadNotification(command)
    }

    @PostMapping
    override fun registerDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: RegisterDeviceTokenRequest,
    ): ResponseEntity<DeviceTokenDetailResponse> {
        val command = RegisterDeviceTokenUseCase.Command(
            userId = userId,
            token = request.token,
        )
        val deviceTokenId = registerDeviceTokenUseCase.registerDeviceToken(command)
        val result = DeviceTokenDetailResponse.of(id = deviceTokenId, userId = userId, token = request.token)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("/tokens/{tokenId}")
    override fun deleteDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @PathVariable tokenId: Long,
    ) {
        val command = DeleteDeviceTokenUseCase.Command(
            userId = userId,
            tokenId = tokenId,
        )
        deleteDeviceTokenUseCase.deleteDeviceToken(command)
    }

    @PatchMapping("/tokens/{tokenId}")
    override fun updateDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @PathVariable tokenId: Long,
        @RequestBody request: UpdateDeviceTokenRequest,
    ) {
        val command = UpdateDeviceTokenUseCase.Command(
            userId = userId,
            tokenId = tokenId,
            token = request.token,
        )
        updateDeviceTokenUseCase.updateDeviceToken(command)
    }

    @GetMapping("/tokens")
    override fun readDeviceToken(
        userId: Long,
        request: ReadDeviceTokenRequest,
    ): ResponseEntity<DeviceTokenDetailResponse> {
        val query = ReadDeviceTokenUseCase.Query(
            userId = userId,
            token = request.token,
        )
        val result = readDeviceTokenUseCase.readDeviceToken(query)
        return ResponseEntity.ok(DeviceTokenDetailResponse.from(result))
    }
}
