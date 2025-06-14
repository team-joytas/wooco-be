package kr.wooco.woocobe.mysql.user

import kr.wooco.woocobe.core.user.application.port.out.UserCommandPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.exception.NotExistsUserException
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import kr.wooco.woocobe.mysql.user.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserQueryPort,
    UserCommandPort {
    override fun getByUserId(userId: Long): User {
        val userJpaEntity = userJpaRepository.findByIdOrNull(userId)
            ?: throw NotExistsUserException
        return UserPersistenceMapper.toDomainEntity(userJpaEntity)
    }

    override fun getOrNullBySocialUser(socialUser: SocialUser): User? {
        val userJpaEntity = userJpaRepository.findBySocialIdAndSocialType(
            socialId = socialUser.socialId,
            socialType = socialUser.socialType.name,
        )
        return userJpaEntity?.let { UserPersistenceMapper.toDomainEntity(it) }
    }

    override fun getAllByUserIds(userIds: List<Long>): List<User> {
        val userJpaEntities = userJpaRepository.findAllById(userIds)
        return userJpaEntities.map { UserPersistenceMapper.toDomainEntity(it) }
    }

    override fun saveUser(user: User): User {
        val userJpaEntity = UserPersistenceMapper.toJpaEntity(user)
        return UserPersistenceMapper.toDomainEntity(userJpaRepository.save(userJpaEntity))
    }

    override fun deleteByUserId(userId: Long) {
        userJpaRepository.deleteById(userId)
    }
}
