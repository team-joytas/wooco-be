package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.NotificationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {
    fun findAllByUserId(userId: Long): List<NotificationJpaEntity>
}
