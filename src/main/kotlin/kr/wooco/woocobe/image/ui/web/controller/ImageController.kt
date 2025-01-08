package kr.wooco.woocobe.image.ui.web.controller

import kr.wooco.woocobe.image.ui.web.controller.response.ImageUploadUrlResponse
import kr.wooco.woocobe.image.ui.web.facade.ImageFacadeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val imageFacadeService: ImageFacadeService,
) : ImageApi {
    @GetMapping("/upload/url")
    override fun getUploadUrl(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<ImageUploadUrlResponse> {
        val response = imageFacadeService.getImageUploadUrl(userId)
        return ResponseEntity.ok(response)
    }
}
