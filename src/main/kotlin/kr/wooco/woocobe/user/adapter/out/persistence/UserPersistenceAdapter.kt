package kr.wooco.woocobe.user.adapter.out.persistence

import kr.wooco.woocobe.user.adapter.out.persistence.repository.UserJpaRepository
import kr.wooco.woocobe.user.application.port.out.DeleteUserPersistencePort
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import kr.wooco.woocobe.user.application.port.out.SaveUserPersistencePort
import kr.wooco.woocobe.user.domain.entity.User
import kr.wooco.woocobe.user.domain.exception.NotExistsUserException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userPersistenceMapper: UserPersistenceMapper,
) : LoadUserPersistencePort,
    SaveUserPersistencePort,
    DeleteUserPersistencePort {
    override fun getByUserId(userId: Long): User {
        val userJpaEntity = userJpaRepository.findByIdOrNull(userId)
            ?: throw NotExistsUserException
        return userPersistenceMapper.toDomain(userJpaEntity)
    }

    override fun getAllByUserIds(userIds: List<Long>): List<User> {
        val userJpaEntities = userJpaRepository.findAllById(userIds)
        return userJpaEntities.map { userPersistenceMapper.toDomain(it) }
    }

    override fun saveUser(user: User): User {
        val userJpaEntity = userPersistenceMapper.toEntity(user)
        return userPersistenceMapper.toDomain(userJpaRepository.save(userJpaEntity))
    }

    override fun deleteByUserId(userId: Long) {
        userJpaRepository.deleteById(userId)
    }
}
