package kr.wooco.woocobe.auth.infrastructure.cache.entity

class AuthTokenRedisEntity(
    val tokenId: String,
    val userId: Long,
)
