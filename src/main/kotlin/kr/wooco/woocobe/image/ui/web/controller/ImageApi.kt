package kr.wooco.woocobe.image.ui.web.controller

import kr.wooco.woocobe.image.ui.web.controller.response.ImageUploadUrlResponse
import org.springframework.http.ResponseEntity

interface ImageApi {
    fun getUploadUrl(userId: Long): ResponseEntity<ImageUploadUrlResponse>
}
