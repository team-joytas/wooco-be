package kr.wooco.woocobe.user.adapter.out.persistence.repository

import kr.wooco.woocobe.user.adapter.out.persistence.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long>
