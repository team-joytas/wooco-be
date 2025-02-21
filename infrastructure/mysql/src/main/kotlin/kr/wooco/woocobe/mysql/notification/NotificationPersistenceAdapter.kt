package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.application.port.out.LoadNotificationPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.SaveNotificationPersistencePort
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.exception.NotExistsNotificationException
import kr.wooco.woocobe.mysql.notification.repository.NotificationJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class NotificationPersistenceAdapter(
    private val notificationJpaRepository: NotificationJpaRepository,
    private val notificationPersistenceMapper: NotificationPersistenceMapper,
) : LoadNotificationPersistencePort,
    SaveNotificationPersistencePort {
    override fun getByNotificationId(id: Long): Notification {
        val notificationJpaEntity = notificationJpaRepository.findByIdOrNull(id)
            ?: throw NotExistsNotificationException
        return notificationPersistenceMapper.toDomain(notificationJpaEntity)
    }

    override fun getAllByUserId(userId: Long): List<Notification> {
        val notificationJpaEntities = notificationJpaRepository.findAllByUserId(userId)
        return notificationJpaEntities.map { notificationPersistenceMapper.toDomain(it) }
    }

    override fun saveNotification(notification: Notification): Notification {
        val notificationJpaEntity = notificationPersistenceMapper.toEntity(notification)
        notificationJpaRepository.save(notificationJpaEntity)
        return notificationPersistenceMapper.toDomain(notificationJpaEntity)
    }
}
