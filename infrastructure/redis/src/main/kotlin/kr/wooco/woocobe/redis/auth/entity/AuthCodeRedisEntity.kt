package kr.wooco.woocobe.redis.auth.entity

class AuthCodeRedisEntity(
    val id: String,
    val verifier: String,
    val challenge: String,
)
