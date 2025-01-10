package kr.wooco.woocobe.user.infrastructure.storage.repository

import kr.wooco.woocobe.user.infrastructure.storage.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long>
