package kr.wooco.woocobe.image.adapter.`in`.web

import kr.wooco.woocobe.image.adapter.`in`.web.response.ImageUploadUrlResponse
import kr.wooco.woocobe.image.application.port.`in`.ReadUploadImageUrlUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val readUploadImageUrlUseCase: ReadUploadImageUrlUseCase,
) : ImageApi {
    @GetMapping("/upload/url")
    override fun getUploadUrl(): ResponseEntity<ImageUploadUrlResponse> {
        val results = readUploadImageUrlUseCase.readUploadImageUrl()
        val response = ImageUploadUrlResponse.from(results)
        return ResponseEntity.ok(response)
    }
}
