package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.exception.NotExistsChallengeCodeException
import kr.wooco.woocobe.auth.domain.gateway.PkceStorageGateway
import kr.wooco.woocobe.auth.domain.model.Pkce
import kr.wooco.woocobe.auth.infrastructure.cache.PkceCacheMapper
import kr.wooco.woocobe.auth.infrastructure.cache.repository.PkceRedisRepository
import org.springframework.stereotype.Component

@Component
internal class PkceStorageGatewayImpl(
    private val pkceRedisRepository: PkceRedisRepository,
    private val pkceCacheMapper: PkceCacheMapper,
) : PkceStorageGateway {
    override fun save(pkce: Pkce): Pkce {
        val pkceEntity = pkceCacheMapper.toEntity(pkce)
        pkceRedisRepository.save(pkceEntity)
        return pkceCacheMapper.toDomain(pkceEntity)
    }

    override fun getWithDeleteByChallenge(challenge: String): Pkce {
        val pkceEntity = pkceRedisRepository.findAndDeleteByChallenge(challenge)
            ?: throw NotExistsChallengeCodeException
        return pkceCacheMapper.toDomain(pkceEntity)
    }
}
