package kr.wooco.woocobe.image.ui.web.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.image.ui.web.controller.response.ImageUploadUrlResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal

@Tag(name = "이미지 API")
interface ImageApi {
    @SecurityRequirement(name = "JWT")
    fun getUploadUrl(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<ImageUploadUrlResponse>
}
