package kr.wooco.woocobe.image.infrastructure.client

import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Component
class S3ClientHandler(
    private val s3Presigner: S3Presigner,
    private val s3ClientProperties: S3ClientProperties,
) {
    fun generateImageUrl(key: String): String = "${s3ClientProperties.baseImageUrl}$PATH_SEPARATOR$key"

    fun generatePresignedUrl(key: String): String {
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
        private const val PATH_SEPARATOR = "/"

        private inline fun PutObjectRequest.Builder.build(options: PutObjectRequest.Builder.() -> Unit) =
            PutObjectRequest.builder().apply(options).build()

        private inline fun PutObjectPresignRequest.Builder.build(options: PutObjectPresignRequest.Builder.() -> Unit) =
            PutObjectPresignRequest.builder().apply(options).build()
    }
}
