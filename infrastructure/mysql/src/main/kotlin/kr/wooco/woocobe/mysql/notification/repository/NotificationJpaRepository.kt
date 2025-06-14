package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.NotificationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {
    @Query(
        """
            SELECT n FROM NotificationJpaEntity n
            WHERE n.id = :id
            AND n.status = :status
        """,
    )
    fun findByIdAndStatus(
        id: Long,
        status: String,
    ): NotificationJpaEntity?

    @Query(
        """
            SELECT n FROM NotificationJpaEntity n
            WHERE n.userId = :userId
            AND n.status = :status
        """,
    )
    fun findAllByUserIdAndStatus(
        userId: Long,
        status: String,
    ): List<NotificationJpaEntity>
}
