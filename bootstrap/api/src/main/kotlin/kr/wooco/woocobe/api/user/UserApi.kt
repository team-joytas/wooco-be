package kr.wooco.woocobe.api.user

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.user.request.UpdateUserRequest
import kr.wooco.woocobe.api.user.response.UserInfoResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원 API")
interface UserApi {
    @SecurityRequirement(name = "JWT")
    fun getCurrentUserInfo(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<UserInfoResponse>

    fun getUserInfo(
        @PathVariable userId: Long,
    ): ResponseEntity<UserInfoResponse>

    @SecurityRequirement(name = "JWT")
    fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun withdrawUser(
        @CookieValue socialToken: String,
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<Unit>
}
