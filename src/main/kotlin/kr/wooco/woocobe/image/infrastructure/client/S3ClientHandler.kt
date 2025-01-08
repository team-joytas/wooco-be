package kr.wooco.woocobe.image.infrastructure.client

import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Component
class S3ClientHandler(
    private val s3Presigner: S3Presigner,
    private val s3ClientProperties: S3ClientProperties,
) {
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

        DeleteObjectsRequest
            .builder()
            .bucket(s3ClientProperties.bucketName)
            .delete(Delete.builder().objects(listOf()).build())
            .build()

        return s3Presigner.presignPutObject(presignRequest).url().toString()
    }
}

// Extension Methods
private inline fun PutObjectRequest.Builder.build(options: PutObjectRequest.Builder.() -> Unit): PutObjectRequest =
    PutObjectRequest.builder().apply(options).build()

private inline fun PutObjectPresignRequest.Builder.build(options: PutObjectPresignRequest.Builder.() -> Unit): PutObjectPresignRequest =
    PutObjectPresignRequest.builder().apply(options).build()
