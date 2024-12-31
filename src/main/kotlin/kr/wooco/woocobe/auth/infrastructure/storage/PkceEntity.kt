package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.auth.domain.model.Pkce

class PkceEntity(
    val verifier: String,
    val challenge: String,
) {
    fun toDomain(): Pkce =
        Pkce(
            verifier = verifier,
            challenge = challenge,
        )

    companion object {
        fun from(pkce: Pkce): PkceEntity =
            PkceEntity(
                verifier = pkce.verifier,
                challenge = pkce.challenge,
            )
    }
}
