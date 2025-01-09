package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.storage.entity.AuthUserJpaEntity
import org.springframework.stereotype.Component

@Component
class AuthUserStorageMapper {
    fun toDomain(authUserJpaEntity: AuthUserJpaEntity): AuthUser =
        AuthUser(
            id = authUserJpaEntity.id,
            userId = authUserJpaEntity.userId,
            socialAuth = SocialAuth(
                socialId = authUserJpaEntity.socialId,
                socialType = SocialType.from(authUserJpaEntity.socialType),
            ),
        )

    fun toEntity(authUser: AuthUser): AuthUserJpaEntity =
        AuthUserJpaEntity(
            id = authUser.id,
            userId = authUser.userId,
            socialId = authUser.socialAuth.socialId,
            socialType = authUser.socialAuth.socialType.name,
        )
}
