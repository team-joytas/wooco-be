package kr.wooco.woocobe.mysql.notification.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "notifications")
class NotificationJpaEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "target_id")
    val targetId: Long,
    @Column(name = "target_name")
    val targetName: String,
    @Column(name = "is_read")
    val isRead: Boolean,
    @Column(name = "type")
    val type: String,
    @Id @Tsid
    @Column(name = "notification_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
