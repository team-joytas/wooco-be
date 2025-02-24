package kr.wooco.woocobe.aws.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
data class S3Properties(
    val bucketName: String,
    val contentType: String,
    val timeout: Duration,
)
