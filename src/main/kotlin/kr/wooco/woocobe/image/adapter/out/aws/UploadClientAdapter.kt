package kr.wooco.woocobe.image.adapter.out.aws

import kr.wooco.woocobe.image.application.port.out.UploadClientPort
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Component
class UploadClientAdapter(
    private val s3Presigner: S3Presigner,
    private val s3ClientProperties: S3ClientProperties,
) : UploadClientPort {
    override fun fetchUploadUrl(key: String): String {
        val putObjectRequest = PutObjectRequest.builder().build {
            key(key)
            bucket(s3ClientProperties.bucketName)
            contentType(s3ClientProperties.contentType)
        }

        val presignRequest = PutObjectPresignRequest.builder().build {
            putObjectRequest(putObjectRequest)
            signatureDuration(s3ClientProperties.timeout)
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
