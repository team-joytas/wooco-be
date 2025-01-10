package kr.wooco.woocobe.user.infrastructure.gateway

import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import kr.wooco.woocobe.user.infrastructure.storage.entity.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class JpaUserStorageGateway(
    private val userJpaRepository: UserJpaRepository,
) : UserStorageGateway {
    override fun save(user: User): User = userJpaRepository.save(UserEntity.from(user)).toDomain()

    override fun update(user: User) = userJpaRepository.save(UserEntity.fromWithId(user)).toDomain()

    override fun getByUserId(userId: Long): User? = userJpaRepository.findByIdOrNull(userId)?.toDomain()

    override fun deleteByUserId(userId: Long) = userJpaRepository.deleteById(userId)
}
