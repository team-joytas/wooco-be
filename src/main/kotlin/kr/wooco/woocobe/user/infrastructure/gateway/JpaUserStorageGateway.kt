package kr.wooco.woocobe.user.infrastructure.gateway

import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaUserStorageGateway(
    private val userJpaRepository: UserJpaRepository,
) : UserStorageGateway {
    override fun save(user: User): User = userJpaRepository.save(UserEntity.from(user)).toDomain()
}
