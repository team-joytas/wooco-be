package kr.wooco.woocobe.image.infrastructure.gateway

import kr.wooco.woocobe.image.domain.gateway.ImageClientGateway
import kr.wooco.woocobe.image.domain.model.ImageKey
import kr.wooco.woocobe.image.infrastructure.client.S3ClientHandler
import org.springframework.stereotype.Component

@Component
internal class ImageClientGatewayImpl(
    private val s3ClientHandler: S3ClientHandler,
) : ImageClientGateway {
    override fun fetchImageUploadUrl(imageKey: ImageKey): String = s3ClientHandler.generatePresignedUrl(imageKey.key)
}
