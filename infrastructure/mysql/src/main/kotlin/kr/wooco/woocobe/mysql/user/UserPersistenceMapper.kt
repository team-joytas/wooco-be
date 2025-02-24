package kr.wooco.woocobe.mysql.user

import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.vo.UserProfile
import kr.wooco.woocobe.core.user.domain.vo.UserStatus
import kr.wooco.woocobe.mysql.user.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
internal class UserPersistenceMapper {
    fun toDomain(userJpaEntity: UserJpaEntity): User =
        User(
            id = userJpaEntity.id,
            profile = UserProfile(
                name = userJpaEntity.name,
                profileUrl = userJpaEntity.profileUrl,
                description = userJpaEntity.description,
            ),
            status = UserStatus(userJpaEntity.status),
        )

    fun toEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            name = user.profile.name,
            profileUrl = user.profile.profileUrl,
            description = user.profile.description,
            status = user.status.name,
        )
}
