package kr.wooco.woocobe.redis.auth

import kr.wooco.woocobe.core.auth.application.port.out.TokenCommandPort
import kr.wooco.woocobe.core.auth.application.port.out.TokenQueryPort
import kr.wooco.woocobe.core.auth.domain.entity.Token
import kr.wooco.woocobe.core.auth.domain.exception.NotExistsTokenException
import kr.wooco.woocobe.redis.auth.repository.TokenRedisRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class TokenRedisAdapter(
    private val tokenRedisRepository: TokenRedisRepository,
) : TokenQueryPort,
    TokenCommandPort {
    override fun getAuthTokenByTokenId(tokenId: Long): Token {
        val tokenRedisEntity = tokenRedisRepository.findByIdOrNull(tokenId)
            ?: throw NotExistsTokenException
        return TokenRedisMapper.toDomainEntity(tokenRedisEntity)
    }

    override fun saveAuthToken(token: Token): Token {
        val tokenRedisEntity = tokenRedisRepository.save(TokenRedisMapper.toRedisEntity(token))
        return TokenRedisMapper.toDomainEntity(tokenRedisEntity)
    }

    override fun deleteAuthToken(token: Token) {
        tokenRedisRepository.deleteById(token.id)
    }
}
