package kr.wooco.woocobe.image.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.image.domain.gateway.ImageClientGateway
import kr.wooco.woocobe.image.domain.model.Image
import kr.wooco.woocobe.image.domain.model.ImageKey
import org.springframework.stereotype.Service

data class GetImageUploadUrlInput(
    val userId: Long,
)

data class GetImageUploadUrlOutput(
    val image: Image,
)

@Service
class GetImageUploadUrlUseCase(
    private val imageClientGateway: ImageClientGateway,
) : UseCase<GetImageUploadUrlInput, GetImageUploadUrlOutput> {
    override fun execute(input: GetImageUploadUrlInput): GetImageUploadUrlOutput {
        val imageKey = ImageKey(input.userId)
        val uploadUrl = imageClientGateway.fetchImageUploadUrl(imageKey)

        val image = Image(imageKey, uploadUrl)

        return GetImageUploadUrlOutput(
            image = image,
        )
    }
}
