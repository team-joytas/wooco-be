package kr.wooco.woocobe.auth.infrastructure.cache

class AuthTokenRedisEntity(
    val tokenId: String,
    val userId: Long,
)
