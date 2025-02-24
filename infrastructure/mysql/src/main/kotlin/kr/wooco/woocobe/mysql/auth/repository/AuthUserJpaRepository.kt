package kr.wooco.woocobe.mysql.auth.repository

import kr.wooco.woocobe.mysql.auth.entity.AuthUserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserJpaRepository : JpaRepository<AuthUserJpaEntity, Long> {
    fun findBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): AuthUserJpaEntity?

    fun deleteByUserId(userId: Long)
}
