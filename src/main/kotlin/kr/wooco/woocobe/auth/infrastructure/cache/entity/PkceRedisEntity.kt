package kr.wooco.woocobe.auth.infrastructure.cache.entity

class PkceRedisEntity(
    val verifier: String,
    val challenge: String,
)
