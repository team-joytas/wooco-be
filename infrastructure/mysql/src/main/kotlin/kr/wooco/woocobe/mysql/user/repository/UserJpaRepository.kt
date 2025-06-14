package kr.wooco.woocobe.mysql.user.repository

import kr.wooco.woocobe.mysql.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {
    fun findBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): UserJpaEntity?
}
