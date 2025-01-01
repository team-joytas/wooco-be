package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.PkceStorageGateway
import kr.wooco.woocobe.auth.domain.model.Pkce
import kr.wooco.woocobe.auth.infrastructure.storage.PkceEntity
import kr.wooco.woocobe.auth.infrastructure.storage.PkceRedisRepository
import org.springframework.stereotype.Component

@Component
internal class RedisPkceStorageGateway(
    private val pkceRedisRepository: PkceRedisRepository,
) : PkceStorageGateway {
    override fun save(pkce: Pkce): Pkce = pkceRedisRepository.save(PkceEntity.from(pkce)).toDomain()

    override fun getWithDeleteByChallenge(challenge: String): Pkce? = pkceRedisRepository.findAndDeleteByChallenge(challenge)?.toDomain()
}
