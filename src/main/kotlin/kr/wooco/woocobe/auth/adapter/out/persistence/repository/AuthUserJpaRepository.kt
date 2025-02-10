package kr.wooco.woocobe.auth.adapter.out.persistence.repository

import kr.wooco.woocobe.auth.adapter.out.persistence.entity.AuthUserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserJpaRepository : JpaRepository<AuthUserJpaEntity, Long> {
    fun findBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): AuthUserJpaEntity?

    fun deleteByUserId(userId: Long)
}
