package kr.wooco.woocobe.core.image.application.service

import kr.wooco.woocobe.core.image.application.port.`in`.ReadUploadImageUrlUseCase
import kr.wooco.woocobe.core.image.application.port.`in`.results.UploadImageUrlResult
import kr.wooco.woocobe.core.image.application.port.out.UploadClientPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ImageQueryService(
    private val uploadClientPort: UploadClientPort,
) : ReadUploadImageUrlUseCase {
    override fun readUploadImageUrl(): UploadImageUrlResult {
        val imageKey = UUID.randomUUID().toString()
        val uploadUrl = uploadClientPort.fetchUploadUrl(imageKey)
        return UploadImageUrlResult.of(imageKey, uploadUrl)
    }
}
