package kr.wooco.woocobe.image.adapter.out.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "app.aws.s3")
data class S3ClientProperties(
    val bucketName: String,
    val contentType: String,
    val timeout: Duration,
)
