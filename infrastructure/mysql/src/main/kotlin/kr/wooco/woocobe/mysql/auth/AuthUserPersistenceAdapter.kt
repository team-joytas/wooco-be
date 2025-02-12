package kr.wooco.woocobe.mysql.auth

import kr.wooco.woocobe.core.auth.application.port.out.DeleteAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.LoadAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.application.port.out.SaveAuthUserPersistencePort
import kr.wooco.woocobe.core.auth.domain.entity.AuthUser
import kr.wooco.woocobe.mysql.auth.repository.AuthUserJpaRepository
import org.springframework.stereotype.Component

@Component
internal class AuthUserPersistenceAdapter(
    private val authUserJpaRepository: AuthUserJpaRepository,
    private val authUserPersistenceMapper: AuthUserPersistenceMapper,
) : LoadAuthUserPersistencePort,
    SaveAuthUserPersistencePort,
    DeleteAuthUserPersistencePort {
    override fun getOrNullBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): AuthUser? {
        val authUser = authUserJpaRepository.findBySocialIdAndSocialType(socialId, socialType)
        return authUser?.let { authUserPersistenceMapper.toDomain(it) }
    }

    override fun saveAuthUser(authUser: AuthUser): AuthUser {
        val authUserJpaEntity = authUserPersistenceMapper.toEntity(authUser)
        authUserJpaRepository.save(authUserJpaEntity)
        return authUserPersistenceMapper.toDomain(authUserJpaEntity)
    }

    override fun deleteByUserId(userId: Long) {
        authUserJpaRepository.deleteByUserId(userId)
    }
}
