package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.auth.domain.model.SocialType
import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserJpaRepository : JpaRepository<AuthUserEntity, Long> {
    fun findBySocialIdAndSocialType(
        socialId: String,
        socialType: SocialType,
    ): AuthUserEntity?

    fun deleteByUserId(userId: Long)
}
