package kr.wooco.woocobe.user.infrastructure.storage

import kr.wooco.woocobe.user.domain.entity.User
import kr.wooco.woocobe.user.infrastructure.storage.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
class UserStorageMapper {
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
