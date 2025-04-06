package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.application.port.out.NotificationCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.exception.NotExistsNotificationException
import kr.wooco.woocobe.core.notification.domain.vo.NotificationStatus
import kr.wooco.woocobe.mysql.notification.repository.NotificationJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class NotificationPersistenceAdapter(
    private val notificationJpaRepository: NotificationJpaRepository,
) : NotificationQueryPort,
    NotificationCommandPort {
    override fun getByNotificationId(notificationId: Long): Notification {
        val notificationJpaEntity = notificationJpaRepository.findByIdOrNull(notificationId)
            ?: throw NotExistsNotificationException
        return NotificationPersistenceMapper.toDomainEntity(notificationJpaEntity)
    }

    override fun getActiveByNotificationId(notificationId: Long): Notification {
        val notificationJpaEntity =
            notificationJpaRepository.findByIdAndStatus(notificationId, NotificationStatus.ACTIVE.name)
                ?: throw NotExistsNotificationException
        return NotificationPersistenceMapper.toDomainEntity(notificationJpaEntity)
    }

    override fun getAllActiveByUserId(userId: Long): List<Notification> =
        notificationJpaRepository
            .findAllByUserIdAndStatus(userId, NotificationStatus.ACTIVE.name)
            .map { NotificationPersistenceMapper.toDomainEntity(it) }

    override fun saveNotification(notification: Notification): Notification {
        val notificationJpaEntity = NotificationPersistenceMapper.toJpaEntity(notification)
        notificationJpaRepository.save(notificationJpaEntity)
        return NotificationPersistenceMapper.toDomainEntity(notificationJpaEntity)
    }
}
