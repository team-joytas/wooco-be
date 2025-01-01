package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.Pkce

interface PkceStorageGateway {
    fun save(pkce: Pkce): Pkce

    fun getWithDeleteByChallenge(challenge: String): Pkce?
}
