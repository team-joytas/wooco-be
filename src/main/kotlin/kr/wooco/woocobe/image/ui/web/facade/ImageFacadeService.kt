package kr.wooco.woocobe.image.ui.web.facade

import kr.wooco.woocobe.image.domain.usecase.GetImageUploadUrlInput
import kr.wooco.woocobe.image.domain.usecase.GetImageUploadUrlUseCase
import kr.wooco.woocobe.image.ui.web.controller.response.ImageUploadUrlResponse
import org.springframework.stereotype.Service

@Service
class ImageFacadeService(
    private val getImageUploadUrlUseCase: GetImageUploadUrlUseCase,
) {
    fun getImageUploadUrl(userId: Long): ImageUploadUrlResponse {
        val result = getImageUploadUrlUseCase.execute(
            GetImageUploadUrlInput(userId = userId),
        )
        return ImageUploadUrlResponse(
            imagePath = result.image.imagePath,
            uploadUrl = result.image.uploadUrl,
        )
    }
}
