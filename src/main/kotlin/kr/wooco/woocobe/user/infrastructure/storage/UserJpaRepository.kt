package kr.wooco.woocobe.user.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long>
