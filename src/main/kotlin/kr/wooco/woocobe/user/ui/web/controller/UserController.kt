package kr.wooco.woocobe.user.ui.web.controller

import kr.wooco.woocobe.user.ui.web.controller.request.UpdateUserRequest
import kr.wooco.woocobe.user.ui.web.controller.response.UserDetailResponse
import kr.wooco.woocobe.user.ui.web.facade.UserFacadeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userFacadeService: UserFacadeService,
) : UserApi {
    @GetMapping("/me")
    override fun getCurrentUser(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<UserDetailResponse> {
        val response = userFacadeService.getUser(userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{userId}")
    override fun getUser(
        @PathVariable userId: Long,
    ): ResponseEntity<UserDetailResponse> {
        val response = userFacadeService.getUser(userId)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/profile")
    override fun updateProfile(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<Unit> {
        userFacadeService.updateUser(userId, request)
        return ResponseEntity.ok().build()
    }
}
