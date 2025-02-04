package kr.wooco.woocobe.user.ui.web.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.user.ui.web.controller.request.UpdateUserRequest
import kr.wooco.woocobe.user.ui.web.controller.response.UserDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원 API")
interface UserApi {
    @SecurityRequirement(name = "JWT")
    fun getCurrentUser(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<UserDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun getUser(
        @PathVariable userId: Long,
    ): ResponseEntity<UserDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<Unit>
}
