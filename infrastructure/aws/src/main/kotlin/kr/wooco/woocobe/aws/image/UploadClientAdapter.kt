package kr.wooco.woocobe.aws.image

import kr.wooco.woocobe.aws.common.S3Properties
import kr.wooco.woocobe.core.image.application.port.out.UploadClientPort
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Component
class UploadClientAdapter(
    private val s3Presigner: S3Presigner,
    private val s3Properties: S3Properties,
) : UploadClientPort {
    override fun fetchUploadUrl(key: String): String {
        val putObjectRequest = PutObjectRequest.builder().build {
            key(key)
            bucket(s3Properties.bucketName)
            contentType(s3Properties.contentType)
        }

        val presignRequest = PutObjectPresignRequest.builder().build {
            putObjectRequest(putObjectRequest)
            signatureDuration(s3Properties.timeout)
        }

        return s3Presigner.presignPutObject(presignRequest).url().toString()
    }

    companion object {
        private inline fun PutObjectRequest.Builder.build(options: PutObjectRequest.Builder.() -> Unit) =
            PutObjectRequest.builder().apply(options).build()

        private inline fun PutObjectPresignRequest.Builder.build(options: PutObjectPresignRequest.Builder.() -> Unit) =
            PutObjectPresignRequest.builder().apply(options).build()
    }
}
