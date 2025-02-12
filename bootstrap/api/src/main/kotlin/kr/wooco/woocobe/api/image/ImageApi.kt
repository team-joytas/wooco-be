package kr.wooco.woocobe.api.image

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.image.response.ImageUploadUrlResponse
import org.springframework.http.ResponseEntity

@Tag(name = "이미지 API")
interface ImageApi {
    @SecurityRequirement(name = "JWT")
    fun getUploadUrl(): ResponseEntity<ImageUploadUrlResponse>
}
