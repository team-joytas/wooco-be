package kr.wooco.woocobe.auth.infrastructure.cache.entity

class PkceEntity(
    val verifier: String,
    val challenge: String,
)
