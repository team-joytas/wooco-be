package kr.wooco.woocobe.user.infrastructure.gateway

import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import kr.wooco.woocobe.user.infrastructure.storage.UserStorageMapper
import kr.wooco.woocobe.user.infrastructure.storage.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class JpaUserStorageGateway(
    private val userJpaRepository: UserJpaRepository,
    private val userStorageMapper: UserStorageMapper,
) : UserStorageGateway {
    override fun save(user: User): User {
        val userEntity = userStorageMapper.toEntity(user)
        userJpaRepository.save(userEntity)
        return userStorageMapper.toDomain(userEntity)
    }

    override fun getByUserId(userId: Long): User? {
        val userEntity = userJpaRepository.findByIdOrNull(userId)
            ?: throw RuntimeException()
        return userStorageMapper.toDomain(userEntity)
    }

    override fun deleteByUserId(userId: Long) {
        userJpaRepository.deleteById(userId)
    }
}
