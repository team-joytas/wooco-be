package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.NotificationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {
    @Query(
        """
            SELECT n FROM NotificationJpaEntity n
            WHERE n.id = :notificationId
            AND n.status = 'ACTIVE'
        """,
    )
    fun findActiveById(notificationId: Long): NotificationJpaEntity?

    @Query(
        """
            SELECT n FROM NotificationJpaEntity n
            WHERE n.userId = :userId
            AND n.status = 'ACTIVE'
        """,
    )
    fun findAllActiveByUserId(userId: Long): List<NotificationJpaEntity>
}
