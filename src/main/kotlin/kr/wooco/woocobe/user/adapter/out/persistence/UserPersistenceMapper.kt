package kr.wooco.woocobe.user.adapter.out.persistence

import kr.wooco.woocobe.user.adapter.out.persistence.entity.UserJpaEntity
import kr.wooco.woocobe.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
internal class UserPersistenceMapper {
    fun toDomain(userJpaEntity: UserJpaEntity): User =
        User(
            id = userJpaEntity.id,
            profile = User.UserProfile(
                name = userJpaEntity.name,
                profileUrl = userJpaEntity.profileUrl,
                description = userJpaEntity.description,
            ),
        )

    fun toEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            name = user.profile.name,
            profileUrl = user.profile.profileUrl,
            description = user.profile.description,
        )
}
