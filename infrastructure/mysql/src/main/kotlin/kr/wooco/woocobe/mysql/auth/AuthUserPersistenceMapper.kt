package kr.wooco.woocobe.mysql.auth

import kr.wooco.woocobe.core.auth.domain.entity.AuthUser
import kr.wooco.woocobe.core.auth.domain.vo.SocialInfo
import kr.wooco.woocobe.core.auth.domain.vo.SocialType
import kr.wooco.woocobe.mysql.auth.entity.AuthUserJpaEntity
import org.springframework.stereotype.Component

@Component
internal class AuthUserPersistenceMapper {
    fun toDomain(authUserJpaEntity: AuthUserJpaEntity): AuthUser =
        AuthUser(
            id = authUserJpaEntity.id,
            userId = authUserJpaEntity.userId,
            socialInfo = SocialInfo(
                socialId = authUserJpaEntity.socialId,
                socialType = SocialType(authUserJpaEntity.socialType),
            ),
        )

    fun toEntity(authUser: AuthUser): AuthUserJpaEntity =
        AuthUserJpaEntity(
            id = authUser.id,
            userId = authUser.userId,
            socialId = authUser.socialInfo.socialId,
            socialType = authUser.socialInfo.socialType.name,
        )
}
