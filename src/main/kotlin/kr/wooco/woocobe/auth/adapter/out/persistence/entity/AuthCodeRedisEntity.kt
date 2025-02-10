package kr.wooco.woocobe.auth.adapter.out.persistence.entity

class AuthCodeRedisEntity(
    val id: String,
    val verifier: String,
    val challenge: String,
)
