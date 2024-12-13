package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialAuthType
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserEntity
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaAuthUserStorageGateway(
    private val authUserJpaRepository: AuthUserJpaRepository,
) : AuthUserStorageGateway {
    override fun save(authUser: AuthUser): AuthUser = authUserJpaRepository.save(AuthUserEntity.from(authUser)).toDomain()

    override fun getByAuthUserId(authUserId: Long): AuthUser? = authUserJpaRepository.findByIdOrNull(authUserId)?.toDomain()

    override fun getBySocialIdAndSocialType(
        socialId: String,
        socialType: SocialAuthType,
    ): AuthUser? = authUserJpaRepository.findBySocialIdAndSocialType(socialId, socialType)?.toDomain()
}