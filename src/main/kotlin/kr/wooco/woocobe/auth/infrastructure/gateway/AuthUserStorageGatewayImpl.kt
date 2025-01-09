package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserStorageMapper
import kr.wooco.woocobe.auth.infrastructure.storage.repository.AuthUserJpaRepository
import org.springframework.stereotype.Component

@Component
internal class AuthUserStorageGatewayImpl(
    private val authUserJpaRepository: AuthUserJpaRepository,
    private val authUserStorageMapper: AuthUserStorageMapper,
) : AuthUserStorageGateway {
    override fun save(authUser: AuthUser): AuthUser {
        val authUserEntity = authUserStorageMapper.toEntity(authUser)
        authUserJpaRepository.save(authUserEntity)
        return authUserStorageMapper.toDomain(authUserEntity)
    }

    override fun getOrNullBySocialIdAndSocialType(
        socialId: String,
        socialType: SocialType,
    ): AuthUser? {
        val authUser = authUserJpaRepository.findBySocialIdAndSocialType(socialId, socialType.name)
        return authUser?.let { authUserStorageMapper.toDomain(it) }
    }

    override fun deleteByUserId(userId: Long) {
        authUserJpaRepository.deleteByUserId(userId)
    }
}
