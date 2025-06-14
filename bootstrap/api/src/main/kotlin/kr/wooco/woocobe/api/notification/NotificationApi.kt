package kr.wooco.woocobe.api.notification

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.notification.request.RegisterDeviceTokenRequest
import kr.wooco.woocobe.api.notification.response.NotificationDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "알림 API")
interface NotificationApi {
    @SecurityRequirement(name = "JWT")
    fun getAllUserNotification(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<NotificationDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun markAsReadNotification(
        @AuthenticationPrincipal userId: Long,
        @PathVariable notificationId: Long,
    )

    @SecurityRequirement(name = "JWT")
    fun registerDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: RegisterDeviceTokenRequest,
    )

    @SecurityRequirement(name = "JWT")
    fun deleteDeviceToken(
        @AuthenticationPrincipal userId: Long,
        @PathVariable token: String,
    )
}
